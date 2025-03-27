package com.example.plusproject.shoppingmall.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {
    /*TotalRating 검색(정렬 포함)*/
    Page<ShoppingMall> findByTotalRating(TotalRating totalRating, Pageable pageable);
    /*StoreStatus 검색(정렬 포함)*/
    Page<ShoppingMall> findByStoreStatus(StoreStatus storeStatus, Pageable pageable);
    /*MonitoringDate 검색(정렬 포함)*/
    Page<ShoppingMall> findByMonitoringDateBetween(LocalDate startDateTime, LocalDate endDateTime, Pageable pageable);
    Page<ShoppingMall> findByMonitoringDateAfter(LocalDate startDateTime, Pageable pageable);
    Page<ShoppingMall> findByMonitoringDateBefore(LocalDate endDateTime, Pageable pageable);
    // 쇼핑몰명이 존재하는지 검색
    boolean existsByStoreName(String storeName);
}
