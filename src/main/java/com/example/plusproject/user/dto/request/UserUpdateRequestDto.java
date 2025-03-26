package com.example.plusproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotNull(message = "수정하려는 이름을 입력해 주세요.")
    private String name;
    @NotNull(message = "현재 비밀번호를 입력해 주세요.")
    private String password;
}
