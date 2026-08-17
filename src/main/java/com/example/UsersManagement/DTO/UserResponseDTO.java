package com.example.UsersManagement.DTO;

import java.util.List;

public record UserResponseDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        List<AddressResponseDTO> addresses
) {

}
