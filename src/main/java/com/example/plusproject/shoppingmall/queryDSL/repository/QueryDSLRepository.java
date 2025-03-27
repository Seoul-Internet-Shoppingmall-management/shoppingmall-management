package com.example.plusproject.shoppingmall.queryDSL.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDSLRepository {

    Page<ShoppingMall> findByTotalRatingAndStoreStatusAfterId(
            TotalRating totalRating,
            StoreStatus storeStatus,
            Long cursorId,
            Pageable pageable
    );
}
