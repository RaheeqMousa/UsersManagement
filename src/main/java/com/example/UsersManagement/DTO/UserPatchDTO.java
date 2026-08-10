package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.User;
import jakarta.validation.constraints.NotNull;

public record UserPatchDTO( String firstName,
String lastName,
String address,
String phoneNumber
){
    public UserPatchDTO(User user){
        this(
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }

}
