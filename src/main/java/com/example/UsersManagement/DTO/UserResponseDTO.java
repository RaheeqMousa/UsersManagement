package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.Address;
import com.example.UsersManagement.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserResponseDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        List<AddressResponseDTO> addresses
) {
    public UserResponseDTO(User user) {
        this(
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getAddresses().stream()
                        .map(address -> new AddressResponseDTO(
                                address.getLongitude(),
                                address.getLatitude(),
                                address.getCity(),
                                address.getStreet()
                        )).toList()
        );
    }

}
