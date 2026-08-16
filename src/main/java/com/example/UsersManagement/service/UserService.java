package com.example.UsersManagement.service;

import com.example.UsersManagement.DTO.UserPatchDTO;
import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.DTO.UserResponseDTO;
import com.example.UsersManagement.entity.User;
import com.example.UsersManagement.exception.UserAlreadyExistException;
import com.example.UsersManagement.exception.UserNotFoundException;
import com.example.UsersManagement.mapper.UserMapper;
import com.example.UsersManagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public final UserRepository userRepository;
    public final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.userMapper= mapper;
    }

    public boolean hasUsers() {
        return userRepository.count() > 0;
    }

    public UserResponseDTO getById(Long id){
        //Optional<User> means that the result might contain a user or may be empty
        Optional<User> user= userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException(
                    "Get by ID - User with id " + id + " not found"
            );
        }
        return userMapper.toResponse(user.get());
    }

    public UserResponseDTO createUser(UserRequestDTO u){
        if(!userRepository.findByPhoneNumber(u.phoneNumber()).isEmpty())
            throw new UserAlreadyExistException("Add User - User with this phone number already exist");
        User userEntity= userMapper.toEntity(u);
        User addedUser= userRepository.save(userEntity);
        return userMapper.toResponse(addedUser);
    }

    public List<UserResponseDTO> getAll(){
        List<User> users= userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }

    public UserResponseDTO getByPhoneNumber(String n){
        Optional<User> userOptional=userRepository.findByPhoneNumber(n);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "Get By phone number - User with phone number " + n + " not found"
            );
        }else
            return userMapper.toResponse(userOptional.get());
    }

    public UserResponseDTO deleteById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "Delete By ID - User with ID " + id + " not found"
            );
        }

        User user = userOptional.get();
        UserResponseDTO response = userMapper.toResponse(user);
        userRepository.delete(user);

        return response;
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userReq) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "Update User - User not found"
            );
        }

        User user = userOptional.get();

        Optional<User> existingUser =
                userRepository.findByPhoneNumber(userReq.phoneNumber());

        if (existingUser.isPresent()
                && !existingUser.get().getId().equals(id)) {

            throw new UserAlreadyExistException(
                    "Update User - User with this phone number already exists"
            );
        }

        user.setAddress(userReq.address());
        user.setLastName(userReq.lastName());
        user.setFirstName(userReq.firstName());
        user.setPhoneNumber(userReq.phoneNumber());

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public UserResponseDTO updatePartOfUser(Long id, UserPatchDTO userReq){
        Optional<User> userOptional= userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("Update user - User not found");
        }else{
            User user=userOptional.get();
            if(userReq.phoneNumber()!=null) {
                Optional<User> existingUser =
                        userRepository.findByPhoneNumber(userReq.phoneNumber());

                if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                    throw new UserAlreadyExistException(
                            "Update part of User - User with this phone number already exists"
                    );
                }

                user.setPhoneNumber(userReq.phoneNumber());
            }if (userReq.address()!=null)
                user.setAddress(userReq.address());
            if (userReq.lastName()!=null)
                user.setLastName(userReq.lastName());
            if(userReq.firstName()!=null)
                user.setFirstName(userReq.firstName());
            User updatedUser= userRepository.save(user);
            return userMapper.toResponse(updatedUser); //return the user object after it is updated
        }
    }

}