package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserPatchDTO(
        String firstName,
        String lastName,
        List<AddressRequestDTO> addresses,
        String phoneNumber
) {

}
