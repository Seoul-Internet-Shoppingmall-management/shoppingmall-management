package com.example.plusproject.shoppingmall.controller;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.dto.ShoppingMallUpdateRequestDto;
import com.example.plusproject.shoppingmall.service.ShoppingMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    @GetMapping("/v1/shopping-malls/{id}")
    public ResponseEntity<ShoppingMallResponseDto> get(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(shoppingMallService.get(id));
    }

    @PatchMapping("/v1/shopping-malls/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody ShoppingMallUpdateRequestDto requestDto
            ) {
        shoppingMallService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

}
