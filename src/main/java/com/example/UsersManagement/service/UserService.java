package com.example.UsersManagement.service;

import com.example.UsersManagement.DTO.AddressRequestDTO;
import com.example.UsersManagement.DTO.UserPatchDTO;
import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.entity.Address;
import com.example.UsersManagement.entity.User;
import com.example.UsersManagement.exception.UserAlreadyExistException;
import com.example.UsersManagement.exception.UserNotFoundException;
import com.example.UsersManagement.mapper.UserMapper;
import com.example.UsersManagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.userMapper = mapper;
    }

    public User getById(Long id) {
        //Optional<User> means that the result might contain a user or may be empty
        Optional<User> user = userRepository.findByIdAndDeletedFalse(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(
                    "Get by ID - User with id " + id + " not found"
            );
        }
        return user.get();
    }

    public User addUser(UserRequestDTO u) {
        User us = userMapper.toEntity(u);
        if (!userRepository.findByPhoneNumberAndDeletedFalse(u.phoneNumber()).isEmpty())
            throw new UserAlreadyExistException("Add User - User with this phone number already exist");
        return userRepository.save(us);
    }

    public Page<User> getUsers(int page, int size) {
        List<User> users= userRepository.findByDeletedFalse();
        int from=size*page;
        if(from>=users.size()){
            return new PageImpl<>(
                    List.of(),
                    PageRequest.of(page, size),
                    users.size()
            );
        }
        int to= Math.min(users.size(), from+size);
        List<User> content= users.subList(from,to);
        return new PageImpl<>(
                content,
                PageRequest.of(page, size),
                users.size()
        );
    }

    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(
                    "Delete By ID - User with ID " + id + " not found"
            );
        }

        User user = optionalUser.get();
        user.setDeleted(true);
        userRepository.save(user);
    }

    public User updateUser(Long id, UserRequestDTO userReq) {

        Optional<User> us = userRepository.findByIdAndDeletedFalse(id);

        if (us.isEmpty()) {
            throw new UserNotFoundException(
                    "Update User - User not found"
            );
        }

        User user = us.get();
        Optional<User> existingUser = userRepository.findByPhoneNumberAndDeletedFalse(userReq.phoneNumber());

        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {

            throw new UserAlreadyExistException(
                    "Update User - User with this phone number already exists"
            );
        }

        user.getAddresses().clear();
        for(AddressRequestDTO req: userReq.addresses()){
            Address address= Address.builder()
                    .longitude(req.longitude())
                    .latitude(req.latitude())
                    .city(req.city())
                    .street(req.street())
                    .user(user)
                    .build();
            user.getAddresses().add(address);
        }

        user.setLastName(userReq.lastName());
        user.setFirstName(userReq.firstName());
        user.setPhoneNumber(userReq.phoneNumber());

        return userRepository.save(user);
    }

    public User updatePartOfUser(Long id, UserPatchDTO userReq) {
        Optional<User> us = userRepository.findByIdAndDeletedFalse(id);
        if (us.isEmpty()) {
            throw new UserNotFoundException("Update user - User not found");
        }
        User user = us.get();
        if (userReq.phoneNumber() != null) {
            Optional<User> existingUser =
                    userRepository.findByPhoneNumberAndDeletedFalse(userReq.phoneNumber());

            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new UserAlreadyExistException(
                        "Update part of User - User with this phone number already exists"
                );
            }

            user.setPhoneNumber(userReq.phoneNumber());
        }
        if (userReq.addresses() != null) {
            user.getAddresses().clear();
            for(AddressRequestDTO req: userReq.addresses()){
                Address address= Address.builder()
                        .longitude(req.longitude())
                        .latitude(req.latitude())
                        .city(req.city())
                        .street(req.street())
                        .user(user)
                        .build();
                user.getAddresses().add(address);
            }
        }if (userReq.lastName() != null)
            user.setLastName(userReq.lastName());
        if (userReq.firstName() != null)
            user.setFirstName(userReq.firstName());
        return userRepository.save(user); //return the user object after it is updated
    }

}