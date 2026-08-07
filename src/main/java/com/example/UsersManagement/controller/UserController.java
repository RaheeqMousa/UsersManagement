package com.example.UsersManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.UsersManagement.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private UserService userService;
    public UserController(UserService uc){
        this.userService=uc;
    }

    @GetMapping("/exist")
    public Map<String,Boolean> usersExist(){
        return Map.of(
                "exist",
                userService.hasUsers()
        );
    }

}
