package com.example.UsersManagement.repository;

import com.example.UsersManagement.entity.User;

import java.util.List;


public interface UserRepositoryCustom {
    List<User> getUsers(
            String firstName,
            String lastName,
            String phoneNumber
    );
}
