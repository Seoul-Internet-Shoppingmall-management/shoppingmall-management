package com.example.plusproject.pageable.controller;

import com.example.plusproject.shoppingmall.controller.ShoppingMallController;
import com.example.plusproject.shoppingmall.dto.response.ShoppingMallResponse;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.service.ShoppingMallService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(ShoppingMallController.class)
class ShoppingMallControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingMallService shoppingMallService;

    private ShoppingMallResponse createDummyResponse() {
        return new ShoppingMallResponse(
                1L, "테스트기업", "테스트스토어", "test.com", "test@email.com",
                "소매업", LocalDate.now(), "서울시 어딘가",
                StoreStatus.OPEN, TotalRating.THREE, "의류",
                "가능", "표기됨", "준수함", "2~3일", "구매자",
                LocalDate.now(), 100L, 101L
        );
    }

    @Test
    @DisplayName("전체 쇼핑몰 조회 성공")
    void getShoppingMalls_success_withoutFilters() throws Exception {
        ShoppingMallResponse response = createDummyResponse();
        Page<ShoppingMallResponse> page = new PageImpl<>(List.of(response));

        Mockito.when(shoppingMallService.getShoppingMalls(null, null, 0, 10)).thenReturn(page);

        mockMvc.perform(get("/v1/shopping-malls")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].storeName").value("테스트스토어"));
    }

    @Test
    @DisplayName("필터 조건으로 쇼핑몰 조회 성공")
    void getShoppingMalls_success_withFilters() throws Exception {
        ShoppingMallResponse response = createDummyResponse();
        Page<ShoppingMallResponse> page = new PageImpl<>(List.of(response));

        Mockito.when(shoppingMallService.getShoppingMalls("OPEN", 2, 0, 10)).thenReturn(page);

        mockMvc.perform(get("/v1/shopping-malls")
                        .param("storeStatus", "ACTIVE")
                        .param("totalRating", "2")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].totalRating").value("THREE"));
    }
}
