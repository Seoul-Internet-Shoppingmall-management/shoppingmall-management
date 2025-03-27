package com.example.plusproject.shoppingmall.queryDSL.controller;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.queryDSL.service.QueryDSLService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryDSLController {

    private final QueryDSLService queryDSLService;

    @GetMapping("/api/v1/shopping-malls/filters/cursor-based")
    public ResponseEntity<Page<ShoppingMall>> getShoppingMall(
            @RequestParam TotalRating totalRating,
            @RequestParam StoreStatus storeStatus,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.asc("id")));
        Page<ShoppingMall> shoppingMalls = queryDSLService.findByShoppingMallsWithCursorId(
                totalRating, storeStatus, cursorId, pageable
        );
        return ResponseEntity.ok(shoppingMalls);
    }
}
