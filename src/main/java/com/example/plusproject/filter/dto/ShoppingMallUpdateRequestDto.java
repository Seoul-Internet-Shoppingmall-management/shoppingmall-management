package com.example.plusproject.filter.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ShoppingMallUpdateRequestDto {

    private String companyName;
    private String storeName;
    private String domainName;
    private String phoneNumber;
    private String operatorEmail;
    private String businessType;
    private LocalDate registrationDate;
    private String companyAddress;
    private String storeStatus;
    private int totalRating;
    private String mainProducts;
    private String subscriptionWithdrawalAvailable;
    private String homepageRequiredItems;
    private String termsOfServiceCompliance;
    private String estimateDeliveryDateDisplay;
    private String withdrawalShippingCostResponsibility;
}
