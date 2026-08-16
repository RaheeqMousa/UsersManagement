package com.example.UsersManagement.mapper;

import com.example.UsersManagement.DTO.AddressResponseDTO;
import com.example.UsersManagement.DTO.UserPatchDTO;
import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.DTO.UserResponseDTO;
import com.example.UsersManagement.entity.Address;
import com.example.UsersManagement.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO dto) {
        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .phoneNumber(dto.phoneNumber())
                .build();

        List<Address> addresses = dto.addresses().stream()
                .map(address ->
                        Address.builder()
                                .longitude(address.longitude())
                                .latitude(address.latitude())
                                .city(address.city())
                                .street(address.street())
                                .user(user)
                                .build())
                                .toList();
        user.setAddresses(addresses);
        return user;
    }

    public User toEntity(UserPatchDTO dto) {
        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .phoneNumber(dto.phoneNumber())
                .build();

        if (dto.addresses() != null) {
            List<Address> addresses = dto.addresses().stream()
                    .map(address -> Address.builder()
                            .longitude(address.longitude())
                            .latitude(address.latitude())
                            .city(address.city())
                            .street(address.street())
                            .user(user)
                            .build())
                    .toList();

            user.setAddresses(addresses);
        }

        return user;
    }

    public UserResponseDTO toResponse(User user) {
        List<AddressResponseDTO> addresses = user.getAddresses().stream()
                .map(address -> new AddressResponseDTO(
                        address.getLongitude(),
                        address.getLatitude(),
                        address.getCity(),
                        address.getStreet()
                ))
                .toList();

        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                addresses
        );
    }
}
