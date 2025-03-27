package com.example.plusproject.shoppingmall.service;

import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.dto.ShoppingMallUpdateRequestDto;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;

    @Transactional(readOnly = true)
    public ShoppingMallResponseDto get(Long id) {

        ShoppingMall shoppingMall = shoppingMallRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_SHOPPING_MALL)
        );

        return ShoppingMallResponseDto.of(shoppingMall);
    }

    @Transactional
    public void update(Long id, ShoppingMallUpdateRequestDto requestDto) {

        ShoppingMall shoppingMall = shoppingMallRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_SHOPPING_MALL)
        );

        shoppingMall.update(
                requestDto.getCompanyName(),
                requestDto.getStoreStatus(),
                requestDto.getDomainName(),
                requestDto.getPhoneNumber(),
                requestDto.getOperatorEmail(),
                requestDto.getBusinessType(),
                requestDto.getRegistrationDate(),
                requestDto.getCompanyAddress(),
                StoreStatus.of(requestDto.getStoreStatus()),
                TotalRating.of(requestDto.getTotalRating()),
                requestDto.getMainProducts(),
                requestDto.getSubscriptionWithdrawalAvailable(),
                requestDto.getHomepageRequiredItems(),
                requestDto.getTermsOfServiceCompliance(),
                requestDto.getEstimateDeliveryDateDisplay(),
                requestDto.getWithdrawalShippingCostResponsibility(),
                LocalDate.now()
                );
    }
}
