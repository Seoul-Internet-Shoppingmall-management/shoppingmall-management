package com.example.plusproject.queryDSL.controller;

import com.example.plusproject.queryDSL.dto.QueryDSLResponseDto;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.queryDSL.service.QueryDSLService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryDSLController {

    private final QueryDSLService queryDSLService;

    @GetMapping("/api/v1/shopping-malls/filters/cursor-based")
    public ResponseEntity<Slice<QueryDSLResponseDto>> getShoppingMall(
            @RequestParam(required = false) TotalRating totalRating,
            @RequestParam(required = false) StoreStatus storeStatus,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Slice<QueryDSLResponseDto> shoppingMalls = queryDSLService.findByShoppingMallsWithCursorId(
                totalRating, storeStatus, cursorId, limit
        );
        return ResponseEntity.ok(shoppingMalls);
    }
}
