package com.example.UsersManagement.service;

import com.example.UsersManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasUsers() {
        return userRepository.count() > 0;
    }
}