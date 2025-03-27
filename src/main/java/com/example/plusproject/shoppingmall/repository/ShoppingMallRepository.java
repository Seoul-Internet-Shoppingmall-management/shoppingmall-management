package com.example.plusproject.shoppingmall.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {
    Page<ShoppingMall> findByStoreStatusAndTotalRating(StoreStatus storeStatus, TotalRating totalRating, Pageable pageable);
    Page<ShoppingMall> findByStoreStatus(StoreStatus storeStatus, Pageable pageable);
    Page<ShoppingMall> findByTotalRating(TotalRating totalRating, Pageable pageable);
}
