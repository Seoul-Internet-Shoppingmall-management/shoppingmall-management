package com.example.plusproject.user.dto;

import com.example.plusproject.user.enums.UserRole;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final UserRole userRole;

    public UserResponseDto(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }
}
