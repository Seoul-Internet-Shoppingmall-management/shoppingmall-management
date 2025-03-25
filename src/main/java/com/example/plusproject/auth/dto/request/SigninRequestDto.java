package com.example.plusproject.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SigninRequestDto {

    @NotNull(message = "이메일을 입력해 주세요.")
    private String email;
    @NotNull(message = "비밀번호를 입력해 주세요.")
    private String password;
}
