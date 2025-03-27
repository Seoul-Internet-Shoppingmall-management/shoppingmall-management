package com.example.plusproject.shoppingmall.repository;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.plusproject.queryDSL.repository.QueryDSLRepository;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long>, QueryDSLRepository {

    @Query("SELECT s FROM ShoppingMall s " +
            "WHERE s.totalRating = :totalRating " +
            "AND s.storeStatus = :storeStatus " +
            "AND s.id > :cursorId " + // 커서ID 이후의 값만 가져오기
            "ORDER BY s.id ASC")  // id 오름차순으로 정렬
    Slice<ShoppingMall> findByTotalRatingAndStoreStatusAfterId(
            @Param("totalRating") TotalRating totalRating,
            @Param("storeStatus") StoreStatus storeStatus,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("SELECT s FROM ShoppingMall s " +
            "WHERE s.totalRating = :totalRating " +
            "AND s.id > :cursorId " + // 커서ID 이후의 값만 가져오기
            "ORDER BY s.id ASC")  // id 오름차순으로 정렬
    Slice<ShoppingMall> findByTotalRatingAfterId(
            @Param("totalRating") TotalRating totalRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable);

    @Query("SELECT s FROM ShoppingMall s " +
            "WHERE s.storeStatus = :storeStatus " +
            "AND s.id > :cursorId " + // 커서ID 이후의 값만 가져오기
            "ORDER BY s.id ASC")  // id 오름차순으로 정렬
    Slice<ShoppingMall> findByStoreStatusAfterId(
            @Param("storeStatus") StoreStatus storeStatus,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /*TotalRating 검색(정렬 포함)*/
    List<ShoppingMall> findByTotalRating(TotalRating totalRating, Sort sort);
    /*StoreStatus 검색(정렬 포함)*/
    List<ShoppingMall> findBystoreStatus(StoreStatus storeStatus, Sort sort);
    /*MonitoringDate 검색(정렬 포함)*/
    List<ShoppingMall> findBymonitoringDateBetween(LocalDate startDateTime, LocalDate endDateTime, Sort sort);
    List<ShoppingMall> findBymonitoringDateAfter(LocalDate startDateTime, Sort sort);
    List<ShoppingMall> findBymonitoringDateBefore(LocalDate endDateTime, Sort sort);
}
