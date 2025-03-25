package com.example.plusproject.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPasswordRequestDto {

    @NotNull(message = "현재 비밀번호를 입력해 주세요.")
    private String password;
}
