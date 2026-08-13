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
    public UserPatchDTO(User user) {
        this(
                user.getFirstName(),
                user.getLastName(),
                user.getAddresses().stream()
                        .map(address -> new AddressRequestDTO(
                                address.getLongitude(),
                                address.getLatitude(),
                                address.getCity(),
                                address.getStreet()
                        )).toList(),
                user.getPhoneNumber()
        );
    }

}
