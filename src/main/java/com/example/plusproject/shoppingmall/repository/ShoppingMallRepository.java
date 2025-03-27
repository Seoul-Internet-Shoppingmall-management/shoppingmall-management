package com.example.plusproject.shoppingmall.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {
    /*TotalRating 검색(정렬 포함)*/
    List<ShoppingMall> findByTotalRating(TotalRating totalRating, Sort sort);
    /*StoreStatus 검색(정렬 포함)*/
    List<ShoppingMall> findBystoreStatus(StoreStatus storeStatus, Sort sort);
    /*MonitoringDate 검색(정렬 포함)*/
    Page<ShoppingMall> findByMonitoringDateBetween(LocalDate startDateTime, LocalDate endDateTime, Pageable pageable);
    Page<ShoppingMall> findByMonitoringDateAfter(LocalDate startDateTime, Pageable pageable);
    Page<ShoppingMall> findByMonitoringDateBefore(LocalDate endDateTime, Pageable pageable);
    // 쇼핑몰명이 존재하는지 검색
    boolean existsByStoreName(String storeName);
    List<ShoppingMall> findBymonitoringDateBetween(LocalDate startDateTime, LocalDate endDateTime, Sort sort);
    List<ShoppingMall> findBymonitoringDateAfter(LocalDate startDateTime, Sort sort);
    List<ShoppingMall> findBymonitoringDateBefore(LocalDate endDateTime, Sort sort);
}
