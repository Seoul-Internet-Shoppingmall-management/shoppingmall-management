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
    void 전체면_해당_평점에_맞는_쇼핑몰_목록을_반환한다() {
        //given
        TotalRating totalRating = TotalRating.TWO;
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
                        totalRating,
                        "주요취급품목1",
                        "청양철회가능여부1",
                        "초기화면필수항목중표시사항1",
                        "약관준수",
                        "2025-12-31",
                        "판매자부담",
                        LocalDate.of(2023, 3, 1))
        );

        when(shoppingMallRepository.findByTotalRating(totalRating, sort)).thenReturn(mockShoppingMalls);

        // When (서비스 메서드 실행)
        List<ShoppingMallResponseDto> result = filterService.findByTotalRating(sort, totalRating);

        // Then (검증)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("가게1", result.get(0).getStoreName());
        assertEquals(4.5, result.get(0).getTotalRating().getRating());
    }
}
