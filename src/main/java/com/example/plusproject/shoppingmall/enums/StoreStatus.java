package com.example.plusproject.shoppingmall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StoreStatus {

    CLOSED("사이트운영중단"),     //사이트 운영 중단
    ON_HOLD("휴업중"),    //휴업중
    ADVERTISEMENT("광고용(홍보용)"),  //광고용(홍보용)
    INFORMATION_MISMATCH("등록정보불일치"),   //등록정보불일치
    SHUTDOWN("사이트폐쇄"),   //사이트폐쇄
    OPEN("영업중"),   //영업중
    PENDING("확인안됨"),    //확인안됨
    NONE("비어있음");   //비어있음

    private final String storeStatus;

    public static StoreStatus of(String status) {
        if (status == null) {
            return null;
        }
        return Arrays.stream(StoreStatus.values())
                .filter(s -> s.storeStatus.equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 StoreStatus 값: " + status));
    }
}
