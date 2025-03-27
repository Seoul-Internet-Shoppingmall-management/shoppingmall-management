package com.example.plusproject.shoppingmall.service;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;

    public Page<ShoppingMall> getShoppingMalls(StoreStatus storeStatus, TotalRating totalRating, Pageable pageable) {

        // 1. 두 개의 필터를 모두 적용
        if (storeStatus != null && totalRating != null) {
            return shoppingMallRepository.findByStoreStatusAndTotalRating(storeStatus, totalRating, pageable);
        }

        // 2. 운영 상태 필터만 적용
        if (storeStatus != null) {
            return shoppingMallRepository.findByStoreStatus(storeStatus, pageable);
        }

        // 3. 전체 평가 필터만 적용
        if (totalRating != null) {
            return shoppingMallRepository.findByTotalRating(totalRating, pageable);
        }

        // 4.필터가 없을 경우 전체 조회
        return shoppingMallRepository.findAll(pageable);
    }
}
