package com.example.plusproject.shoppingmall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TotalRating {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);

    private final int value;

    public static TotalRating of(int value) {
        return Arrays.stream(TotalRating.values())
                .filter(r -> r.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 TotalRating 값: " + value));
    }
}
