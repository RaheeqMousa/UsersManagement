package com.example.UsersManagement.DTO;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UserRequestDTO(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull List<AddressRequestDTO> addresses,
        @NotNull String phoneNumber
) {


}