package com.example.plusproject.shoppingmall.service;

import com.example.plusproject.shoppingmall.dto.response.ShoppingMallResponse;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;

    @Transactional(readOnly = true)
    public Page<ShoppingMallResponse> getShoppingMalls(StoreStatus storeStatus, TotalRating totalRating, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ShoppingMall> result;

        if (storeStatus != null && totalRating != null) {
            result = shoppingMallRepository.findByStoreStatusAndTotalRating(storeStatus, totalRating, pageable);
        } else if (storeStatus != null) {
            result = shoppingMallRepository.findByStoreStatus(storeStatus, pageable);
        } else if (totalRating != null) {
            result = shoppingMallRepository.findByTotalRating(totalRating, pageable);
        } else {
            result = shoppingMallRepository.findAll(pageable);
        }

        List<ShoppingMallResponse> responseList = new ArrayList<>();
        for (ShoppingMall mall : result.getContent()) {
            responseList.add(new ShoppingMallResponse(
                    mall.getId(),
                    mall.getCompanyName(),
                    mall.getStoreName(),
                    mall.getDomainName(),
                    mall.getOperatorEmail(),
                    mall.getBusinessType(),
                    mall.getRegistrationDate(),
                    mall.getCompanyAddress(),
                    mall.getStoreStatus(),
                    mall.getTotalRating(),
                    mall.getMainProducts(),
                    mall.getSubscriptionWithdrawalAvailable(),
                    mall.getHomepageRequiredItems(),
                    mall.getTermsOfServiceCompliance(),
                    mall.getEstimateDeliveryDateDisplay(),
                    mall.getWithdrawalShippingCostResponsibility(),
                    mall.getMonitoringDate(),
                    mall.getCreatedBy(),
                    mall.getModifiedBy()
            ));
        }

        return new PageImpl<>(responseList, pageable, result.getTotalElements());
    }
}
