package com.example.plusproject.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {

    @NotNull(message = "현재 비밀번호를 입력해 주세요.")
    private String currentPassword;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$", message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함하며 8자 이상이어야 합니다.")
    @NotNull(message = "새로운 비밀번호를 입력해 주세요.")
    private String newPassword;
}
