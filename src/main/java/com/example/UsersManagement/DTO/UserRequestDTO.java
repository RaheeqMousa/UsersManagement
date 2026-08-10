package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.User;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(@NotNull String firstName,
    @NotNull String lastName,
    @NotNull String address,
@NotNull String phoneNumber
){
public UserRequestDTO(User user){
    this(
            user.getFirstName(),
            user.getLastName(),
            user.getAddress(),
            user.getPhoneNumber()
    );
}

}