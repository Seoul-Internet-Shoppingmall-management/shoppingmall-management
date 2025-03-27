package com.example.plusproject.shoppingmall.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.queryDSL.repository.QueryDSLRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long>, QueryDSLRepository {

    Page<ShoppingMall> findByTotalRatingAndStoreStatus(
            @Param("totalRating") TotalRating totalRating,
            @Param("storeStatus") StoreStatus storeStatus,
            Pageable pageable
    );

    @Query("SELECT s FROM ShoppingMall s " +
            "WHERE s.totalRating = :totalRating " +
            "AND s.storeStatus = :storeStatus " +
            "AND s.id > :cursorId " + // 커서ID 이후의 값만 가져오기
            "ORDER BY s.id ASC")  // id 오름차순으로 정렬
    Page<ShoppingMall> findByTotalRatingAndStoreStatusAfterId(
            @Param("totalRating") TotalRating totalRating,
            @Param("storeStatus") StoreStatus storeStatus,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
