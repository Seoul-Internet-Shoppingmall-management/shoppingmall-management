package com.example.plusproject.queryDSL.service;

import com.example.plusproject.queryDSL.dto.QueryDSLResponseDto;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryDSLService {

    private final ShoppingMallRepository shoppingMallRepository;

    @Transactional(readOnly = true)
    public Slice<QueryDSLResponseDto> findByShoppingMallsWithCursorId(
            TotalRating totalRating,
            StoreStatus storeStatus,
            Long cursorId,
            int limit
    ) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.asc("id")));

        Slice<ShoppingMall> shoppingMalls;

        if (cursorId == null) {
            throw new IllegalStateException("마지막 위치가 확인되지 않습니다.");
        }
        if (totalRating != null && storeStatus != null) {
            // 두 가지가 모두 있을 경우
            shoppingMalls = shoppingMallRepository.findByTotalRatingAndStoreStatusAfterId(totalRating, storeStatus, cursorId, pageable);
        } else if (totalRating != null) {
            // 전체 평가만 존재하는 경우
            shoppingMalls = shoppingMallRepository.findByTotalRatingAfterId(totalRating, cursorId, pageable);
        } else if (storeStatus != null) {
            // 영업상태만 존재하는 경우
            shoppingMalls = shoppingMallRepository.findByStoreStatusAfterId(storeStatus, cursorId, pageable);
        } else {
            // 둘 다 없는 경우에 대한 처리가 필요하면 추가
            shoppingMalls = shoppingMallRepository.findAll(pageable); // 예시로 모든 데이터를 반환하는 경우
        }

        return shoppingMalls.map(QueryDSLResponseDto::of);
    }
}