package com.example.plusproject.shoppingmall.dto.response;

import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
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
    private final String homepageRequriedItems;
    private final String termsOfServiceCompliance;
    private final String estimateDeliveryDateDisplay;
    private final String withdrawalShippingCostResopnsibility;
    private final LocalDate monitoringDate;
    private final User createdBy;
    private final User modifiedBy;

    public ShoppingMallResponse(
            Long id,
            String company,
            String storeName,
            String domainName,
            String operatorEmail,
            String businessType,
            LocalDate registrationDate,
            String companyAddress,
            StoreStatus storestatus,
            TotalRating totalRating,
            String mainProducts,
            String subscriptionWithdrawalAvailable,
            String homepageRequiredItems,
            String termsOfServiceCompliance,
            String estimateDeliveryDateDisplay,
            String withdrawalShippingCostResponsibility,
            LocalDate monitoringDate,
            User createdBy,
            User modifiedBy
    ) {
        this.id = id;
        this.companyName = company;
        this.storeName = storeName;
        this.domainName = domainName;
        this.operatorEmail = operatorEmail;
        this.businessType = businessType;
        this.registrationDate = registrationDate;
        this.companyAddress = companyAddress;
        this.storeStatus = storestatus;
        this.totalRating = totalRating;
        this.mainProducts = mainProducts;
        this.subscriptionWithdrawalAvailable = subscriptionWithdrawalAvailable;
        this.homepageRequriedItems = homepageRequiredItems;
        this.termsOfServiceCompliance = termsOfServiceCompliance;
        this.estimateDeliveryDateDisplay = estimateDeliveryDateDisplay;
        this.withdrawalShippingCostResopnsibility = withdrawalShippingCostResponsibility;
        this.monitoringDate = monitoringDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }
}
