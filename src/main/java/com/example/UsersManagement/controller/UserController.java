package com.example.UsersManagement.controller;

import com.example.UsersManagement.DTO.UserPatchDTO;
import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.DTO.UserResponseDTO;
import com.example.UsersManagement.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.example.UsersManagement.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;
    public UserController(UserService uc){
        this.userService=uc;
    }

    @GetMapping("users/exist")
    public Map<String,Boolean> usersExist(){
        return Map.of(
                "exist",
                userService.hasUsers()
        );
    }

    @GetMapping("users")
    public List<UserResponseDTO> getAll(){
        return userService.getAll();
    }

    @GetMapping("users/{id}")
    public UserResponseDTO getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping("users/phone/{phone_number}")
    public UserResponseDTO getByPhone(@PathVariable String phone_number){
        return userService.getByPhoneNumber(phone_number);
    }

    @PostMapping("users")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request){
        return userService.createUser(request);
    }

    @DeleteMapping("users/{id}")
    public UserResponseDTO deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }

    @PutMapping("users/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO req){
        return userService.updateUser(id,req);
    }

    @PatchMapping("users/{id}")
    public UserResponseDTO updatePartOfUser(@PathVariable Long id, @RequestBody UserPatchDTO req){
        return userService.updatePartOfUser(id,req);
    }

}
