package com.example.UsersManagement.DTO;

import java.util.List;

public record UserPatchDTO(
        String firstName,
        String lastName,
        List<AddressRequestDTO> addresses,
        String phoneNumber
) {

}
