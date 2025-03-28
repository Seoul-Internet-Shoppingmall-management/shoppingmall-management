package com.example.plusproject.filter.serviceTest;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.service.FilterService;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilterServiceTest {
    @Mock
    private ShoppingMallRepository shoppingMallRepository;

    @InjectMocks
    private FilterService filterService;

    @Test
    void 전체평점에_맞는_쇼핑몰_목록을_반환한다() {
        //given
        TotalRating totalRating = TotalRating.TWO;
        Sort sort = Sort.by(Sort.Direction.ASC, "totalRating");

        List<ShoppingMall> mockShoppingMalls = List.of(
                new ShoppingMall(
                        "상호1",
                        "쇼핑몰1",
                        "도메인1.com",
                        "010-1234-5678",
                        "email1@example.com",
                        "영업형태1",
                        LocalDate.of(2020, 1, 1),
                        "서울시 강남구",
                        StoreStatus.OPEN,
                        totalRating,
                        "주요취급품목1",
                        "청양철회가능여부1",
                        "초기화면필수항목중표시사항1",
                        "약관준수",
                        "2025-12-31",
                        "판매자부담",
                        LocalDate.of(2023, 3, 1))
        );

        BDDMockito.given(shoppingMallRepository.findByTotalRating(any(), any())).willReturn(mockShoppingMalls);

        //when
        List<ShoppingMallResponseDto> result = filterService.findByTotalRating(sort, totalRating);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("쇼핑몰1", result.get(0).getStoreName());
        assertEquals(2, result.get(0).getTotalRating().getValue());
    }





    @Test
    void 업소상태에_맞는_쇼핑몰_목록을_반환한다() {
        //given
        StoreStatus storeStatus = StoreStatus.OPEN;
        Sort sort = Sort.by(Sort.Direction.ASC, "totalRating");

        List<ShoppingMall> mockShoppingMalls = List.of(
                new ShoppingMall(
                        "상호1",
                        "쇼핑몰1",
                        "도메인1.com",
                        "010-1234-5678",
                        "email1@example.com",
                        "영업형태1",
                        LocalDate.of(2020, 1, 1),
                        "서울시 강남구",
                        storeStatus,
                        TotalRating.TWO,
                        "주요취급품목1",
                        "청양철회가능여부1",
                        "초기화면필수항목중표시사항1",
                        "약관준수",
                        "2025-12-31",
                        "판매자부담",
                        LocalDate.of(2023, 3, 1))
        );

        BDDMockito.given(shoppingMallRepository.findByStoreStatus(any(), any())).willReturn(mockShoppingMalls);

        //when
        List<ShoppingMallResponseDto> result = filterService.findByStoreStatus(sort, storeStatus);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("쇼핑몰1", result.get(0).getStoreName());
        assertEquals("영업중", result.get(0).getStoreStatus().getStoreStatus());
    }





    @Test
    void 모니터링날짜start_end에_맞는_쇼핑몰_목록을_반환한다() {
        //given
        String startDate = "2023-02-25";
        String endDate = "2023-03-02";
        Sort sort = Sort.by(Sort.Direction.ASC, "totalRating");

        List<ShoppingMall> mockShoppingMalls = List.of(
                new ShoppingMall(
                        "상호1",
                        "쇼핑몰1",
                        "도메인1.com",
                        "010-1234-5678",
                        "email1@example.com",
                        "영업형태1",
                        LocalDate.of(2020, 1, 1),
                        "서울시 강남구",
                        StoreStatus.OPEN,
                        TotalRating.TWO,
                        "주요취급품목1",
                        "청양철회가능여부1",
                        "초기화면필수항목중표시사항1",
                        "약관준수",
                        "2025-12-31",
                        "판매자부담",
                        LocalDate.of(2023, 3, 1))
        );

        BDDMockito.given(shoppingMallRepository.findByMonitoringDateBetween(any(), any(), any())).willReturn(mockShoppingMalls);

        //when
        List<ShoppingMallResponseDto> result = filterService.findByMonitoringDate(sort, startDate, endDate);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("쇼핑몰1", result.get(0).getStoreName());
        assertEquals(LocalDate.of(2023, 3, 1), result.get(0).getMonitoringDate());
    }





    @Test
    void 모니터링날짜start에_맞는_쇼핑몰_목록을_반환한다() {
        //given
        String startDate = "2023-02-25";
        String endDate = null;
        Sort sort = Sort.by(Sort.Direction.ASC, "totalRating");

        List<ShoppingMall> mockShoppingMalls = List.of(
                new ShoppingMall(
                        "상호1",
                        "쇼핑몰1",
                        "도메인1.com",
                        "010-1234-5678",
                        "email1@example.com",
                        "영업형태1",
                        LocalDate.of(2020, 1, 1),
                        "서울시 강남구",
                        StoreStatus.OPEN,
                        TotalRating.TWO,
                        "주요취급품목1",
                        "청양철회가능여부1",
                        "초기화면필수항목중표시사항1",
                        "약관준수",
                        "2025-12-31",
                        "판매자부담",
                        LocalDate.of(2023, 3, 1))
        );

        BDDMockito.given(shoppingMallRepository.findByMonitoringDateAfter(any(), any())).willReturn(mockShoppingMalls);

        //when
        List<ShoppingMallResponseDto> result = filterService.findByMonitoringDate(sort, startDate, endDate);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("쇼핑몰1", result.get(0).getStoreName());
        assertEquals(LocalDate.of(2023, 3, 1), result.get(0).getMonitoringDate());
    }





    @Test
    void 모니터링날짜end에_맞는_쇼핑몰_목록을_반환한다() {
        //given
        String startDate = null;
        String endDate = "2023-03-02";
        Sort sort = Sort.by(Sort.Direction.ASC, "totalRating");

        List<ShoppingMall> mockShoppingMalls = List.of(
                new ShoppingMall(
                        "상호1",
                        "쇼핑몰1",
                        "도메인1.com",
                        "010-1234-5678",
                        "email1@example.com",
                        "영업형태1",
                        LocalDate.of(2020, 1, 1),
                        "서울시 강남구",
                        StoreStatus.OPEN,
                        TotalRating.TWO,
                        "주요취급품목1",
                        "청양철회가능여부1",
                        "초기화면필수항목중표시사항1",
                        "약관준수",
                        "2025-12-31",
                        "판매자부담",
                        LocalDate.of(2023, 3, 1))
        );

        BDDMockito.given(shoppingMallRepository.findByMonitoringDateBefore(any(), any())).willReturn(mockShoppingMalls);

        //when
        List<ShoppingMallResponseDto> result = filterService.findByMonitoringDate(sort, startDate, endDate);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("쇼핑몰1", result.get(0).getStoreName());
        assertEquals(LocalDate.of(2023, 3, 1), result.get(0).getMonitoringDate());
    }
}
