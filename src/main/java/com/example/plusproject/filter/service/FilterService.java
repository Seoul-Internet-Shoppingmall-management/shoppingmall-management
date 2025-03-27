package com.example.plusproject.filter.service;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//repository 메서드 이용
//페이지네이션 이용(정렬)
@Service
@RequiredArgsConstructor
public class FilterService {
    private final ShoppingMallRepository shoppingMallRepository;

    /*다건 조회 todo: TotalRating 검색*/
    @Transactional(readOnly = true)
    public Page<ShoppingMallResponseDto> findTotalRatingFilter(Pageable pageable, TotalRating totalRating) {
        Page<ShoppingMall> shoppingMalls;

        if(totalRating!=null){ shoppingMalls = shoppingMallRepository.findBytotalRating(totalRating, pageable); }//totalRating 검색
        else{ shoppingMalls = shoppingMallRepository.findAll(pageable); }//검색 지정이 없는 경우, 모두 출력

        return shoppingMalls.map(shoppingMall -> new ShoppingMallResponseDto(
                shoppingMall.getCompanyName(),
                shoppingMall.getStoreName(),
                shoppingMall.getDomainName(),
                shoppingMall.getPhoneNumber(),
                shoppingMall.getOperatorEmail(),
                shoppingMall.getBusinessType(),
                shoppingMall.getRegistrationDate(),
                shoppingMall.getCompanyAddress(),
                shoppingMall.getStoreStatus(),
                shoppingMall.getTotalRating(),
                shoppingMall.getMainProducts(),
                shoppingMall.getSubscriptionWithdrawalAvailable(),
                shoppingMall.getHomepageRequiredItems(),
                shoppingMall.getTermsOfServiceCompliance(),
                shoppingMall.getEstimateDeliveryDateDisplay(),
                shoppingMall.getWithdrawalShippingCostResponsibility(),
                shoppingMall.getMonitoringDate()
                )
        );
    }



    /*다건 조회 todo: StoreStatus 검색*/
    @Transactional(readOnly = true)
    public Page<ShoppingMallResponseDto> findStoreStatusFilter(Pageable pageable, StoreStatus storeStatus) {
        Page<ShoppingMall> shoppingMalls;

        if(storeStatus!=null){ shoppingMalls = shoppingMallRepository.findBystoreStatus(storeStatus, pageable); }//storeStatus 검색
        else{ shoppingMalls = shoppingMallRepository.findAll(pageable); }//검색 지정이 없는 경우, 모두 출력

        return shoppingMalls.map(shoppingMall -> new ShoppingMallResponseDto(
                        shoppingMall.getCompanyName(),
                        shoppingMall.getStoreName(),
                        shoppingMall.getDomainName(),
                        shoppingMall.getPhoneNumber(),
                        shoppingMall.getOperatorEmail(),
                        shoppingMall.getBusinessType(),
                        shoppingMall.getRegistrationDate(),
                        shoppingMall.getCompanyAddress(),
                        shoppingMall.getStoreStatus(),
                        shoppingMall.getTotalRating(),
                        shoppingMall.getMainProducts(),
                        shoppingMall.getSubscriptionWithdrawalAvailable(),
                        shoppingMall.getHomepageRequiredItems(),
                        shoppingMall.getTermsOfServiceCompliance(),
                        shoppingMall.getEstimateDeliveryDateDisplay(),
                        shoppingMall.getWithdrawalShippingCostResponsibility(),
                        shoppingMall.getMonitoringDate()
                )
        );
    }



    /*다건 조회 todo: MonitoringDate 검색*/
    @Transactional(readOnly = true)
    public Page<ShoppingMallResponseDto> findMonitoringDateFilter(Pageable pageable, String monitoringStartDate, String monitoringEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDateTime = null;
        LocalDate endDateTime = null;
        if(monitoringStartDate!=null) startDateTime = LocalDate.parse(monitoringStartDate, formatter);//형식 변환
        if(monitoringEndDate!=null) endDateTime = LocalDate.parse(monitoringEndDate, formatter);//형식 변환

        Page<ShoppingMall> shoppingMalls;

        if(startDateTime!=null && endDateTime!=null){ shoppingMalls = shoppingMallRepository.findBymonitoringDateBetween(startDateTime, endDateTime, pageable); }//monitoringDate 검색
        else if(startDateTime!=null){shoppingMalls = shoppingMallRepository.findBymonitoringDateAfter(startDateTime, pageable);}
        else if(endDateTime!=null){shoppingMalls = shoppingMallRepository.findBymonitoringDateBefore(endDateTime, pageable);}
        else{ shoppingMalls = shoppingMallRepository.findAll(pageable); }//검색 지정이 없는 경우, 모두 출력

        return shoppingMalls.map(shoppingMall -> new ShoppingMallResponseDto(
                        shoppingMall.getCompanyName(),
                        shoppingMall.getStoreName(),
                        shoppingMall.getDomainName(),
                        shoppingMall.getPhoneNumber(),
                        shoppingMall.getOperatorEmail(),
                        shoppingMall.getBusinessType(),
                        shoppingMall.getRegistrationDate(),
                        shoppingMall.getCompanyAddress(),
                        shoppingMall.getStoreStatus(),
                        shoppingMall.getTotalRating(),
                        shoppingMall.getMainProducts(),
                        shoppingMall.getSubscriptionWithdrawalAvailable(),
                        shoppingMall.getHomepageRequiredItems(),
                        shoppingMall.getTermsOfServiceCompliance(),
                        shoppingMall.getEstimateDeliveryDateDisplay(),
                        shoppingMall.getWithdrawalShippingCostResponsibility(),
                        shoppingMall.getMonitoringDate()
                )
        );
    }
}
