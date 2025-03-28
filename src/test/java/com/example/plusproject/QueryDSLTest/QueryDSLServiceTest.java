package com.example.plusproject.QueryDSLTest;

import com.example.plusproject.queryDSL.dto.QueryDSLResponseDto;
import com.example.plusproject.queryDSL.repository.QueryDSLRepository;
import com.example.plusproject.queryDSL.service.QueryDSLService;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QueryDSLServiceTest {

    @Mock
    private ShoppingMallRepository shoppingMallRepository;

    @Mock
    private QueryDSLRepository queryDSLRepository;

    @InjectMocks
    private QueryDSLService queryDSLService;

    @Test
    void cursorID를_기반으로_totalRating과_storeStatus를_기준으로_조회할_수_있다() {
        //given
        long cursorId = 5L;
        TotalRating totalRating = TotalRating.ONE;
        StoreStatus storeStatus = StoreStatus.OPEN;
        int limit = 10;

        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.asc("id")));

        ShoppingMall mockShoppingMall = new ShoppingMall(
                "companyName", "storeName", "domainName", "phoneNumber", "a@a.com", "businessType",
                LocalDate.now(), "companyAddress", storeStatus, totalRating, "mainProduct", "subscription_",
                "homepage_", "terms_", "estimate_", "withdrawal_",
                LocalDate.now());
        //하나의 단일 객체로만 구성된 리스트 생성
        List<ShoppingMall> shoppingMallList = Collections.singletonList(mockShoppingMall);
        Slice<ShoppingMall> shoppingMallSlice = mock();
        when(shoppingMallRepository.findByTotalRatingAndStoreStatusAfterId(totalRating, storeStatus, cursorId, pageable))
                .thenReturn(shoppingMallSlice);
        //when
        Slice<QueryDSLResponseDto> result = queryDSLService.findByShoppingMallsWithCursorId(totalRating, storeStatus, cursorId, limit);

        //then
        verify(shoppingMallRepository, times(1))
                .findByTotalRatingAndStoreStatusAfterId(totalRating, storeStatus, cursorId, pageable);
    }

    @Test
    void cursorID를_기반으로_totalRating을_기준으로_조회할_수_있다() {
        //given
        long cursorId = 5L;
        TotalRating totalRating = TotalRating.ONE;
        int limit = 10;

        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.asc("id")));

        ShoppingMall mockShoppingMall = new ShoppingMall(
                "companyName", "storeName", "domainName", "phoneNumber", "a@a.com", "businessType",
                LocalDate.now(), "companyAddress", null, totalRating, "mainProduct", "subscription_",
                "homepage_", "terms_", "estimate_", "withdrawal_",
                LocalDate.now());
        //하나의 단일 객체로만 구성된 리스트 생성
        List<ShoppingMall> shoppingMallList = Collections.singletonList(mockShoppingMall);
        Slice<ShoppingMall> shoppingMallSlice = mock();
        when(shoppingMallRepository.findByTotalRatingAfterId(totalRating, cursorId, pageable))
                .thenReturn(shoppingMallSlice);
        //when
        Slice<QueryDSLResponseDto> result = queryDSLService.findByShoppingMallsWithCursorId(totalRating, null, cursorId, limit);

        //then
        verify(shoppingMallRepository, times(1))
                .findByTotalRatingAfterId(totalRating, cursorId, pageable);
    }

    @Test
    void cursorID를_기반으로_storeStatus를_기준으로_조회할_수_있다() {
        //given
        long cursorId = 5L;
        StoreStatus storeStatus = StoreStatus.OPEN;
        int limit = 10;

        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Order.asc("id")));

        ShoppingMall mockShoppingMall = new ShoppingMall(
                "companyName", "storeName", "domainName", "phoneNumber", "a@a.com", "businessType",
                LocalDate.now(), "companyAddress", storeStatus, null, "mainProduct", "subscription_",
                "homepage_", "terms_", "estimate_", "withdrawal_",
                LocalDate.now());
        //하나의 단일 객체로만 구성된 리스트 생성
        List<ShoppingMall> shoppingMallList = Collections.singletonList(mockShoppingMall);
        Slice<ShoppingMall> shoppingMallSlice = mock();
        when(shoppingMallRepository.findByStoreStatusAfterId(storeStatus, cursorId, pageable))
                .thenReturn(shoppingMallSlice);
        //when
        Slice<QueryDSLResponseDto> result = queryDSLService.findByShoppingMallsWithCursorId(null, storeStatus, cursorId, limit);

        //then
        verify(shoppingMallRepository, times(1))
                .findByStoreStatusAfterId(storeStatus, cursorId, pageable);
    }
}
