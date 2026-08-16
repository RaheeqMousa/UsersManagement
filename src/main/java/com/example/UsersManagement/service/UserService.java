package com.example.UsersManagement.service;
import com.example.UsersManagement.entity.Address;
import com.example.UsersManagement.entity.User;
import com.example.UsersManagement.exception.UserAlreadyExistException;
import com.example.UsersManagement.exception.UserNotFoundException;
import com.example.UsersManagement.mapper.UserMapper;
import com.example.UsersManagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public User createUser(User user) {
        if (!userRepository.findByPhoneNumberAndDeletedFalse(user.getPhoneNumber()).isEmpty())
            throw new UserAlreadyExistException("Add User - User with this phone number already exist");
        return userRepository.save(user);
    }

    public Page<User> getUsers(
            String firstName,
            String lastName,
            String phoneNumber,
            Pageable pageable
    ) {
        if(pageable.getPageSize()>20){
            throw new IllegalArgumentException("Page size can't be greater than 20");
        }
        return userRepository.getUsers(
                firstName,
                lastName,
                phoneNumber,
                pageable
        );
    }

    public void deleteById(Long id) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "Delete By ID - User with ID " + id + " not found"
            );
        }

        User user = userOptional.get();
        user.setDeleted(true);
        userRepository.save(user);
    }

    public User updateUser(Long id, User userReq) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "Update User - User not found"
            );
        }

        User user = userOptional.get();
        Optional<User> existingUser = userRepository.findByPhoneNumberAndDeletedFalse(userReq.getPhoneNumber());

        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {

            throw new UserAlreadyExistException(
                    "Update User - User with this phone number already exists"
            );
        }

        user.getAddresses().clear();
        for(Address req: userReq.getAddresses()){
            Address address= Address.builder()
                    .longitude(req.getLongitude())
                    .latitude(req.getLatitude())
                    .city(req.getCity())
                    .street(req.getStreet())
                    .user(user)
                    .build();
            user.getAddresses().add(address);
        }

        user.setLastName(userReq.getLastName());
        user.setFirstName(userReq.getFirstName());
        user.setPhoneNumber(userReq.getPhoneNumber());

        return userRepository.save(user);
    }

    public User updatePartOfUser(Long id, User userReq) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Update user - User not found");
        }
        User user = userOptional.get();
        if (userReq.getPhoneNumber() != null) {
            Optional<User> existingUser =
                    userRepository.findByPhoneNumberAndDeletedFalse(userReq.getPhoneNumber());

            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new UserAlreadyExistException(
                        "Update part of User - User with this phone number already exists"
                );
            }

            user.setPhoneNumber(userReq.getPhoneNumber());
        }
        if (userReq.getAddresses() != null) {
            user.getAddresses().clear();
            for(Address req: userReq.getAddresses()){
                Address address= Address.builder()
                        .longitude(req.getLongitude())
                        .latitude(req.getLatitude())
                        .city(req.getCity())
                        .street(req.getStreet())
                        .user(user)
                        .build();
                user.getAddresses().add(address);
            }
        }if (userReq.getLastName() != null)
            user.setLastName(userReq.getLastName());
        if (userReq.getFirstName() != null)
            user.setFirstName(userReq.getFirstName());
        return userRepository.save(user); //return the user object after it is updated
    }

}