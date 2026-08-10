package com.example.UsersManagement.mapper;

import com.example.UsersManagement.DTO.UserRequestDTO;
import com.example.UsersManagement.DTO.UserResponseDTO;
import com.example.UsersManagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO dto){
        return User.builder().firstName(dto.firstName())
                .lastName(dto.lastName())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber()).build();
    }

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user);
    }
}
