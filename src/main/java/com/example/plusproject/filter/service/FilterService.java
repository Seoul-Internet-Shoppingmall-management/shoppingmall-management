package com.example.plusproject.filter.service;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

//repository 메서드 이용
//페이지네이션 이용(정렬)
@Service
@RequiredArgsConstructor
public class FilterService {
    private final ShoppingMallRepository shoppingMallRepository;

    /*다건 조회 todo: TotalRating 검색*/
    @Transactional(readOnly = true)
    public List<ShoppingMallResponseDto> findByTotalRating(Sort sort, TotalRating totalRating) {
        List<ShoppingMall> shoppingMalls;

        if (totalRating!=null) {
            shoppingMalls = shoppingMallRepository.findByTotalRating(totalRating, sort);//totalRating 검색
        } else {
            shoppingMalls = shoppingMallRepository.findAll(sort);//검색 지정이 없는 경우, 모두 출력
        }

        return shoppingMalls.stream()
                .map(shoppingMall -> new ShoppingMallResponseDto(
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
                        shoppingMall.getMonitoringDate(),
                        shoppingMall.getCreatedAt(),
                        shoppingMall.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }



    /*다건 조회 todo: StoreStatus 검색*/
    @Transactional(readOnly = true)
    public List<ShoppingMallResponseDto> findByStoreStatus(Sort sort, StoreStatus storeStatus) {
        List<ShoppingMall> shoppingMalls;

        if (storeStatus!=null) {
            shoppingMalls = shoppingMallRepository.findByStoreStatus(storeStatus, sort);//storeStatus 검색
        } else {
            shoppingMalls = shoppingMallRepository.findAll(sort);//검색 지정이 없는 경우, 모두 출력
        }

        return shoppingMalls.stream()
                .map(shoppingMall -> new ShoppingMallResponseDto(
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
                        shoppingMall.getMonitoringDate(),
                        shoppingMall.getCreatedAt(),
                        shoppingMall.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }



    /*다건 조회 todo: MonitoringDate 검색*/
    @Transactional(readOnly = true)
    public List<ShoppingMallResponseDto> findByMonitoringDate(Sort sort, String monitoringStartDate, String monitoringEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDateTime = null;
        LocalDate endDateTime = null;
        if(monitoringStartDate!=null) startDateTime = LocalDate.parse(monitoringStartDate, formatter);//형식 변환
        if(monitoringEndDate!=null) endDateTime = LocalDate.parse(monitoringEndDate, formatter);//형식 변환

        List<ShoppingMall> shoppingMalls;

        if(startDateTime!=null && endDateTime!=null){ shoppingMalls = shoppingMallRepository.findByMonitoringDateBetween(startDateTime, endDateTime, sort); }//monitoringDate 검색
        else if(startDateTime!=null){shoppingMalls = shoppingMallRepository.findByMonitoringDateAfter(startDateTime, sort);}
        else if(endDateTime!=null){shoppingMalls = shoppingMallRepository.findByMonitoringDateBefore(endDateTime, sort);}
        else{ shoppingMalls = shoppingMallRepository.findAll(sort); }//검색 지정이 없는 경우, 모두 출력

        return shoppingMalls.stream()
                .map(shoppingMall -> new ShoppingMallResponseDto(
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
                        shoppingMall.getMonitoringDate(),
                        shoppingMall.getCreatedAt(),
                        shoppingMall.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }
}
