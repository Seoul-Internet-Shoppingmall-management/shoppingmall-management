package com.example.plusproject.shoppingmall.entity;

import com.example.plusproject.shoppingmall.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "seoul-of-shopping-malls")
public class ShoppingMall {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String storeName;
    private String domainName;
    private String phoneNumber;
    private String operatorEmail;
    private String businessType;
    private LocalDate registrationDate;
    private String companyAddress;
    private StoreStatus storeStatus;
    private int totalRating;
    private String mainProducts;
    private String subscriptionWithdrawalAvailable;
    private String homepageRequiredItems;
    private String termsOfServiceCompliance;
    private String estimateDeliveryDateDisplay;
    private String withdrawalShippingCostResponsibility;
    private LocalDate monitoringDate;

}
