package com.example.UsersManagement.controller;

import com.example.UsersManagement.DTO.UserPatchDTO;
import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.DTO.UserResponseDTO;
import com.example.UsersManagement.entity.User;
import com.example.UsersManagement.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.example.UsersManagement.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserMapper userMapper;
    private UserService userService;

    public UserController(UserService uc, UserMapper mapper) {
        this.userService = uc;
        this.userMapper = mapper;
    }

    @GetMapping
    public Page<UserResponseDTO> getUsersJPQL(@RequestParam(required = false) String firstName,
          @RequestParam(required = false) String lastName,
          @RequestParam(required = false) String phoneNumber,
                                          Pageable pageable) {
        Page<User> users = userService.getUsersJPQL(
                firstName,
                lastName,
                phoneNumber,
                pageable);

        return users.map(userMapper::toResponse);
    }

    @GetMapping(value="/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toResponse(user);
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request){
        User user = userMapper.toEntity(request);
        User createdUser= userService.createUser(user);
        return userMapper.toResponse(createdUser);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO req) {
        User userToUpdate=userMapper.toEntity(req);
        User updatedUser = userService.updateUser(id, userToUpdate);
        return userMapper.toResponse(updatedUser);
    }

    @PatchMapping("/{id}")
    public UserResponseDTO updatePartOfUser(@PathVariable Long id, @RequestBody UserPatchDTO req) {
        User userToUpdate=userMapper.toEntity(req);
        User updatedUser = userService.updatePartOfUser(id, userToUpdate);
        return userMapper.toResponse(updatedUser);
    }

}
