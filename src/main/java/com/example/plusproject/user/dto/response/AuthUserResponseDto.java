package com.example.plusproject.user.dto.response;

import com.example.plusproject.user.entity.User;
import com.example.plusproject.user.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUserResponseDto {

    private final Long id;
    private final String email;
    private final UserRole userRole;

    public AuthUserResponseDto(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static AuthUserResponseDto of(User user) {
        return new AuthUserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUserRole()
        );
    }
}
