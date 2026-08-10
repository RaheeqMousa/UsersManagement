package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.User;
import jakarta.validation.constraints.NotNull;

public record UserResponseDTO(
     String firstName,
     String lastName,
     String phoneNumber,
     String address
){
    public UserResponseDTO(User user){
        this(
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getAddress()
        );
    }

}
