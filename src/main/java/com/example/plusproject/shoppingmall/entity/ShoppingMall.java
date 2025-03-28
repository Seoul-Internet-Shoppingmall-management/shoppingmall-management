package com.example.plusproject.shoppingmall.entity;

import com.example.plusproject.common.entity.BaseEntity;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "shopping_malls_of_seoul",
indexes = {@Index(name = "idx_shopping_mall_total_rating_store_status_id", columnList = "total_rating, store_status, id")})
public class ShoppingMall extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상호
    @Column(nullable = false)
    private String companyName;

    // 쇼핑몰명
    private String storeName;

    // 도메인명
    @Column(name="domainName",columnDefinition="TEXT")
    private String domainName;

    // 전화번호
    private String phoneNumber;

    // 운영자 이메일
    private String operatorEmail;

    // 영업형태
    private String businessType;

    // 최초신고일자
    private LocalDate registrationDate;

    // 회사주소
    private String companyAddress;

    // 업소상태
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    // 전체평가
    @Enumerated(EnumType.STRING)
    private TotalRating totalRating;

    // 주요취급품목
    private String mainProducts;

    // 청약철회가능여부
    private String subscriptionWithdrawalAvailable;

    // 초기화면필수항목
    private String homepageRequiredItems;

    // 이용약관준수정도
    private String termsOfServiceCompliance;

    // 배송예정일표시
    private String estimateDeliveryDateDisplay;

    // 철회시배송비부담
    private String withdrawalShippingCostResponsibility;

    // 모니터링날짜
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
