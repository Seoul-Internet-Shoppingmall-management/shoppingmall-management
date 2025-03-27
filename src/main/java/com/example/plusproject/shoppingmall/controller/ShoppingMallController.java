package com.example.plusproject.shoppingmall.controller;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.service.ShoppingMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    // 쇼핑몰을 페이징해서 조회(필터 적용 가능)
    @GetMapping("/api/v1/shopping-malls")
    public ResponseEntity<Page<ShoppingMall>> getShoppingMalls(
            @RequestParam(required = false) StoreStatus storeStatus,
            @RequestParam(required = false) TotalRating totalRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(shoppingMallService.getShoppingMalls(storeStatus, totalRating, pageable));
    }
}
