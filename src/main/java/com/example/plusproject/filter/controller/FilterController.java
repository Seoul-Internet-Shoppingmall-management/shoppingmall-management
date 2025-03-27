package com.example.plusproject.filter.controller;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.service.FilterService;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//repository 메서드 이용
//페이지네이션 이용(정렬)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FilterController {
    private final FilterService FilterService;

    /*다건 조회 todo: TotalRating 검색*/
    @GetMapping("/v1/shopping-malls/filters/overall-rating")
    public ResponseEntity<List<ShoppingMallResponseDto>> findTotalRatingFilter(
            @PageableDefault(page=0, size = 10, sort="companyName", direction = Sort.Direction.ASC) Pageable pageable,//10개씩     companyName기준 ASC정렬
            @RequestParam(required = false) TotalRating totalRating
    ){
        return ResponseEntity.ok(FilterService.findTotalRatingFilter(pageable, totalRating).getContent());
    }

    /*다건 조회 todo: StoreStatus 검색*/
    @GetMapping("/v1/shopping-malls/filters/business-status")
    public ResponseEntity<List<ShoppingMallResponseDto>> findTotalRatingFilter(
            @PageableDefault(page=0, size = 10, sort="companyName", direction = Sort.Direction.ASC) Pageable pageable,//10개씩     companyName기준 ASC정렬
            @RequestParam(required = false) StoreStatus storeStatus
    ){
        return ResponseEntity.ok(FilterService.findStoreStatusFilter(pageable, storeStatus).getContent());
    }

    /*다건 조회 todo: MonitoringDate 검색(기간)*/
    @GetMapping("/v1/shopping-malls/filters/monitoring-date")
    public ResponseEntity<List<ShoppingMallResponseDto>> findMonitoringDateFilter(
            @PageableDefault(page=0, size = 10, sort="companyName", direction = Sort.Direction.ASC) Pageable pageable,//10개씩     companyName기준 ASC정렬
            //@PageableDefault(page=0, size = 10, sort="monitoringDate", direction = Sort.Direction.ASC) Pageable pageable,//10개씩     monitoringDate기준 ASC정렬
            @RequestParam(required = false) String monitoringStartDate,
            @RequestParam(required = false) String monitoringEndDate
    ){
        return ResponseEntity.ok(FilterService.findMonitoringDateFilter(pageable, monitoringStartDate, monitoringEndDate).getContent());
    }
}
