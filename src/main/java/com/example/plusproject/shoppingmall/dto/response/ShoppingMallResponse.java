package com.example.plusproject.shoppingmall.dto.response;

import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ShoppingMallResponse {

    private final Long id;
    private final String companyName;
    private final String storeName;
    private final String domainName;
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
    private final Long createdById;
    private final Long modifiedById;
}
