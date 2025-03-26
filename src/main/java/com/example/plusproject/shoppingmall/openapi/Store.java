package com.example.plusproject.shoppingmall.openapi;

import com.example.plusproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "stores")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String domainName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String operatorEmail;

    @Column(nullable = false)
    private String businessType;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private String companyAddress;

    @Column(nullable = false)
    private String storeStatus;

    @Column(nullable = false)
    private Integer totalRating;

    @Column(nullable = false)
    private String mainProducts;

    @Column(nullable = false)
    private String subscriptionWithdrawalAvailable;

    @Column(nullable = false)
    private String homepageRequiredItems;

    @Column(nullable = false)
    private String termsOfServiceCompliance;

    @Column(nullable = false)
    private String estimateDeliveryDateDisplay;

    @Column(nullable = false)
    private String withdrawalShippingCostResponsibility;

    @Column(nullable = false)
    private LocalDateTime monitoringDate;
}
