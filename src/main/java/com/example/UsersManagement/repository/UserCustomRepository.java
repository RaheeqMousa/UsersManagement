package com.example.UsersManagement.repository;

import com.example.UsersManagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<User> getUsers(
            String firstName,
            String lastName,
            String phoneNumber,
            Pageable pageable
    );
}
