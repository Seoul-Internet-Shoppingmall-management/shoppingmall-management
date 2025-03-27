package com.example.plusproject.shoppingmall.entity;

import com.example.plusproject.common.entity.BaseEntity;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "shopping_malls_of_seoul")
public class ShoppingMall extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 우선 17개 필드값만 입력해 두었습니다. validation 필요한 것 체크할 예정
    private String companyName;
    private String storeName;
    private String domainName;
    private String phoneNumber;
    private String operatorEmail;
    private String businessType;
    private LocalDate registrationDate;
    private String companyAddress;
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;
    @Enumerated(EnumType.STRING)
    private TotalRating totalRating;
    private String mainProducts;
    private String subscriptionWithdrawalAvailable;
    private String homepageRequiredItems;
    private String termsOfServiceCompliance;
    private String estimateDeliveryDateDisplay;
    private String withdrawalShippingCostResponsibility;
    private LocalDate monitoringDate;

}
