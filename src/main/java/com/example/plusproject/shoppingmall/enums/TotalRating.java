package com.example.plusproject.shoppingmall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TotalRating {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);

    private final int value;
}
