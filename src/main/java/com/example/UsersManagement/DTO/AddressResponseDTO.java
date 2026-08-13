package com.example.UsersManagement.DTO;

import com.example.UsersManagement.entity.Address;
import jakarta.validation.constraints.NotNull;

public record AddressResponseDTO(
        @NotNull double longitude,
        @NotNull double latitude,
        @NotNull String city,
        @NotNull String street
) {
    public AddressResponseDTO(Address ad) {
        this(
                ad.getLongitude(),
                ad.getLatitude(),
                ad.getCity(),
                ad.getStreet()
        );
    }
}
