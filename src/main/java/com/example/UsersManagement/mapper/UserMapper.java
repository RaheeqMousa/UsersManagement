package com.example.UsersManagement.mapper;

import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.DTO.UserResponseDTO;
import com.example.UsersManagement.entity.Address;
import com.example.UsersManagement.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO dto) {
        User user = User.builder().firstName(dto.firstName())
                .lastName(dto.lastName())
                .phoneNumber(dto.phoneNumber()).build();

        List<Address> addresses = dto.addresses().stream()
                .map( address ->
                    Address.builder()
                            .longitude(35.217040)
                            .latitude(31.923798)
                            .city("Ramallah")
                            .street("Al-Irsal")
                            .user(user)
                            .build()
                ).toList();
        user.setAddresses(addresses);
        return user;
    }

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user);
    }
}
