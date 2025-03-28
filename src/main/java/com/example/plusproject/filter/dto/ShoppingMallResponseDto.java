package com.example.plusproject.filter.dto;

import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ShoppingMallResponseDto {

    private final String companyName;
    private final String storeName;
    private final String domainName;
    private final String phoneNumber;
    private final String operatorEmail;
    private final String businessType;
    private final LocalDate registrationDate;
    private final String companyAddress;
    private final StoreStatus storeStatus;
    private final TotalRating totalRating;
    private final String mainProducts;
    private final String subscriptionWithdrawalAvailable;
    private final String homepageRequiredItems;
    private final String termsOfServiceCompliance;
    private final String estimateDeliveryDateDisplay;
    private final String withdrawalShippingCostResponsibility;
    private final LocalDate monitoringDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
