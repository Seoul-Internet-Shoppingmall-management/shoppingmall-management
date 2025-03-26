package com.example.plusproject.shoppingmall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {

    CLOSED,     //사이트 운영 중단
    ON_HOLD,    //휴업중
    ADVERTISEMENT,  //광고용(홍보용)
    INFORMAITON_MISMATCH,   //등록정보불일치
    SHUTDOWN,   //사이트폐쇄
    OPEN,   //영업중
    PENDING     //확인안됨
}
