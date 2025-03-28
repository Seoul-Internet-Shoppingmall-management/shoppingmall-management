package com.example.plusproject.pageable.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.plusproject.shoppingmall.dto.response.ShoppingMallResponse;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import com.example.plusproject.shoppingmall.service.ShoppingMallService;
import com.example.plusproject.user.entity.User;
import com.example.plusproject.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ShoppingMallServiceTest {

    @InjectMocks
    private ShoppingMallService shoppingMallService;

    @Mock
    private ShoppingMallRepository shoppingMallRepository;

    private final PageRequest pageable = PageRequest.of(0, 10);

    private ShoppingMall createDummyMall() {
        User createdBy = createUser(100L);
        User modifiedBy = createUser(101L);

        ShoppingMall mall = ShoppingMall.builder()
                .companyName("테스트기업")
                .storeName("테스트몰")
                .domainName("mall.com")
                .operatorEmail("test@mail.com")
                .businessType("도소매업")
                .registrationDate(LocalDate.now())
                .companyAddress("서울시 강남구")
                .storeStatus(StoreStatus.OPEN)
                .totalRating(TotalRating.THREE)
                .mainProducts("전자제품")
                .subscriptionWithdrawalAvailable("가능")
                .homepageRequiredItems("표기")
                .termsOfServiceCompliance("준수")
                .estimateDeliveryDateDisplay("3~5일")
                .withdrawalShippingCostResponsibility("구매자")
                .monitoringDate(LocalDate.now())
                .createdBy(createdBy)
                .modifiedBy(modifiedBy)
                .build();

        // 리플렉션으로 id 강제 설정
        try {
            Field idField = ShoppingMall.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(mall, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mall;
    }

    private User createUser(Long id) {
        User user = User.builder()
                .name("테스터")
                .email("test@example.com")
                .password("pw")
                .userRole(UserRole.ROLE_ADMIN)
                .build();

        try {
            Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Test
    void 필터_없을때_전체조회() {
        Page<ShoppingMall> result = new PageImpl<>(List.of(createDummyMall()));
        when(shoppingMallRepository.findAll(pageable)).thenReturn(result);

        Page<ShoppingMallResponse> response = shoppingMallService.getShoppingMalls(null, null, 0, 10);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).getStoreName()).isEqualTo("테스트몰");
    }

    @Test
    void 상태_필터_조회() {
        Page<ShoppingMall> result = new PageImpl<>(List.of(createDummyMall()));
        when(shoppingMallRepository.findByStoreStatus(eq(StoreStatus.OPEN), eq(pageable))).thenReturn(result);

        Page<ShoppingMallResponse> response = shoppingMallService.getShoppingMalls("OPEN", null, 0, 10);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getStoreStatus()).isEqualTo(StoreStatus.OPEN);
    }

    @Test
    void 평점_필터_조회() {
        Page<ShoppingMall> result = new PageImpl<>(List.of(createDummyMall()));
        when(shoppingMallRepository.findByTotalRating(eq(TotalRating.THREE), eq(pageable))).thenReturn(result);

        Page<ShoppingMallResponse> response = shoppingMallService.getShoppingMalls(null, 2, 0, 10);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getTotalRating()).isEqualTo(TotalRating.THREE);
    }

    @Test
    void 상태_평점_모두_필터_조회() {
        Page<ShoppingMall> result = new PageImpl<>(List.of(createDummyMall()));
        when(shoppingMallRepository.findByStoreStatusAndTotalRating(
                eq(StoreStatus.OPEN), eq(TotalRating.THREE), eq(pageable)))
                .thenReturn(result);

        Page<ShoppingMallResponse> response = shoppingMallService.getShoppingMalls("OPEN", 2, 0, 10);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getCreatedById()).isEqualTo(100L);
    }
}


