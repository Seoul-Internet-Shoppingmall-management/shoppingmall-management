package com.example.plusproject.shoppingmall.openapi;

public class ImportResponseDto {
    private String message;
    private int insertedRows;

    public ImportResponseDto(String message, int insertedRows) {
        this.message = message;
        this.insertedRows = insertedRows;
    }
}
