package com.example.plusproject.user.dto.response;

import com.example.plusproject.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String name;
    private final String email;

    public UserResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
