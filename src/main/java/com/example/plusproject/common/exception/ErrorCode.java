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
    NOT_FOUND_USER("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_SHOPPING_MALL("존재하지 않는 쇼핑몰 입니다.", HttpStatus.NOT_FOUND),

    // USER CLASS ERROR
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    SELFSAME_PASSWORD("동일한 비밀번호로는 수정이 불가합니다.", HttpStatus.BAD_REQUEST),
    UNDELETED_USER("탈퇴 요청되지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),
    DELETED_USER("탈퇴 요청된 사용자입니다.", HttpStatus.BAD_REQUEST),

    // FILTER REQUEST NULL ERROR
    FILTER_REQUEST_NULL("필터할 값을 인식할 수 없습니다.", HttpStatus.BAD_REQUEST),
      
    // SHOPPING_MALLS CLASS ERROR
<<<<<<< HEAD
    EMPTY_FILE("파일이 비어 있습니다.", HttpStatus.BAD_REQUEST);
=======
    EMPTY_FILE("파일이 비어 있습니다.", HttpStatus.BAD_REQUEST),
    NOT_INT_VALUE_OF_TOTAL_RATING("전체 평가는 숫자로 입력해 주세요.", HttpStatus.BAD_REQUEST)
    ;
>>>>>>> f6e6c7bb6444161625773da59f00f6bb4383ecee

    private final String message;
    private final HttpStatus httpStatus;
}
