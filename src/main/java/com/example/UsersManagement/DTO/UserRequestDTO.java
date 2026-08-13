package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserRequestDTO(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull List<AddressRequestDTO> addresses,
        @NotNull String phoneNumber
) {
    public UserRequestDTO(User user) {
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