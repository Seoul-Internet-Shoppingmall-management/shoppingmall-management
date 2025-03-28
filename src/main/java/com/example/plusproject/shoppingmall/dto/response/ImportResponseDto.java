package com.example.plusproject.shoppingmall.dto.response;

import lombok.Getter;

@Getter
public class ImportResponseDto {
    private final String message;
    private final int insertedRows;

    public ImportResponseDto(String message, int insertedRows) {
        this.message = message;
        this.insertedRows = insertedRows;
    }
}
