package com.example.plusproject.shoppingmall.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {
    /*TotalRating 검색(정렬 포함)*/
    Page<ShoppingMall> findBytotalRating(TotalRating totalRating, Pageable pageable);
    /*StoreStatus 검색(정렬 포함)*/
    Page<ShoppingMall> findBystoreStatus(StoreStatus storeStatus, Pageable pageable);
    /*MonitoringDate 검색(정렬 포함)*/
    Page<ShoppingMall> findBymonitoringDateBetween(LocalDate startDateTime, LocalDate endDateTime, Pageable pageable);
    Page<ShoppingMall> findBymonitoringDateAfter(LocalDate startDateTime, Pageable pageable);
    Page<ShoppingMall> findBymonitoringDateBefore(LocalDate endDateTime, Pageable pageable);
}
