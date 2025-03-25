package com.example.plusproject.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // SIGNUP ERROR
    ALREADY_EXIST_EMAIL("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST),

    // NOT FOUND ERROR
    NOT_FOUND_USER("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND)
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
