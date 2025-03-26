package com.example.plusproject.shoppingmall.openapi;

import com.example.plusproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "stores")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 상호
    @Column(nullable = false)
    private String companyName;

    // 쇼핑몰명
    @Column(unique = true)
    private String storeName;

    // 도메인명
    private String domainName;

    // 전화번호
    private String phoneNumber;

    // 운영자 이메일
    private String operatorEmail;

    // 영업형태
    private String businessType;

    // 최초신고일자
    private LocalDateTime registrationDate;

    // 회사주소
    private String companyAddress;

    // 업소상태
    private String storeStatus;

    // 전체평가
    private Integer totalRating;

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
    private LocalDateTime monitoringDate;
}
