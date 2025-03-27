package com.example.plusproject.shoppingmall.entity;

import com.example.plusproject.common.entity.BaseEntity;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy_id", nullable = false)
    @CreatedBy
    private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifiedBy_id", nullable = false)
    @LastModifiedBy
    private User modifiedBy;

    @Builder
    public ShoppingMall(
            String companyName,
            String storeName,
            String domainName,
            String phoneNumber,
            String operatorEmail,
            String businessType,
            LocalDate registrationDate,
            String companyAddress,
            StoreStatus storeStatus,
            TotalRating totalRating,
            String mainProducts,
            String subscriptionWithdrawalAvailable,
            String homepageRequiredItems,
            String termsOfServiceCompliance,
            String estimateDeliveryDateDisplay,
            String withdrawalShippingCostResponsibility,
            LocalDate monitoringDate
    ) {
        this.companyName = companyName;
        this.storeName = storeName;
        this.domainName = domainName;
        this.phoneNumber = phoneNumber;
        this.operatorEmail = operatorEmail;
        this.businessType = businessType;
        this.registrationDate = registrationDate;
        this.companyAddress = companyAddress;
        this.storeStatus = storeStatus;
        this.totalRating = totalRating;
        this.mainProducts = mainProducts;
        this.subscriptionWithdrawalAvailable = subscriptionWithdrawalAvailable;
        this.homepageRequiredItems = homepageRequiredItems;
        this.termsOfServiceCompliance = termsOfServiceCompliance;
        this.estimateDeliveryDateDisplay = estimateDeliveryDateDisplay;
        this.withdrawalShippingCostResponsibility = withdrawalShippingCostResponsibility;
        this.monitoringDate = monitoringDate;
    }

    public void changeStoreStatus(StoreStatus storeStatus) {
        this.storeStatus = storeStatus;
    }

    public void changeTotalRating(TotalRating totalRating) {
        this.totalRating = totalRating;
    }

    public void changeMonitoringDate(LocalDate now) {
        this.monitoringDate = now;
    }
}
