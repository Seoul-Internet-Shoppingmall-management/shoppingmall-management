package com.example.plusproject.shoppingmall.queryDSL.service;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryDSLService {

    private final ShoppingMallRepository shoppingMallRepository;

    @Transactional
    public Page<ShoppingMall> findByShoppingMallsWithCursorId(
            TotalRating totalRating,
            StoreStatus storeStatus,
            Long cursorId,
            Pageable pageable
    ) {
        //cursorId가 null이면 처음부터 결과값 반환
        if (cursorId == null) {
            return shoppingMallRepository.findByTotalRatingAndStoreStatus(totalRating,storeStatus,pageable);
        }
        return shoppingMallRepository.findByTotalRatingAndStoreStatusAfterId(totalRating,storeStatus,cursorId,pageable);
    }
}