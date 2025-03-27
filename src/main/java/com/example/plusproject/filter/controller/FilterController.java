package com.example.plusproject.filter.controller;

import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.service.FilterService;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    private final FilterService filterService;

    /*다건 조회 todo: TotalRating & StoreStatus & MonitoringDate 검색*/
    @GetMapping("/v1/shopping-malls/filters")
    public ResponseEntity<List<ShoppingMallResponseDto>> findFilter(
            @RequestParam(required = false) TotalRating totalRating, //TotalRating
            @RequestParam(required = false) StoreStatus storeStatus, //StoreStatus
            @RequestParam(required = false) String monitoringStartDate, //MonitoringDate
            @RequestParam(required = false) String monitoringEndDate //MonitoringDate
    ){
        Sort sort1 = Sort.by(Sort.Direction.fromString("ASC"), "companyName");//ASC 정렬       //정렬 기준: companyName
        Sort sort2 = Sort.by(Sort.Direction.fromString("ASC"), "monitoringDate");//ASC 정렬       //정렬 기준: monitoringDate

        if(totalRating!=null && storeStatus==null && monitoringStartDate==null && monitoringEndDate==null){//TotalRating
            return ResponseEntity.ok(filterService.findByTotalRating(sort1, totalRating));
        }
        else if(totalRating==null && storeStatus!=null && monitoringStartDate==null && monitoringEndDate==null){//StoreStatus
            return ResponseEntity.ok(filterService.findByStoreStatus(sort1, storeStatus));
        }
        else if(totalRating==null && storeStatus==null && (monitoringStartDate!=null || monitoringEndDate!=null)){ //MonitoringDate
            return ResponseEntity.ok(filterService.findByMonitoringDate(sort2, monitoringStartDate, monitoringEndDate));
        }
        else{
            throw new ApplicationException(ErrorCode.FILTER_REQUEST_NULL);
        }
    }
}
