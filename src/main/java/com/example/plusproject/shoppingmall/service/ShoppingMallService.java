package com.example.plusproject.shoppingmall.service;

import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.shoppingmall.dto.response.ShoppingMallResponse;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;

    private static final int BATCH_SIZE = 100;

    @Transactional(readOnly = true)
    public Page<ShoppingMallResponse> getShoppingMalls(String storeStatus, Integer totalRating, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ShoppingMall> result;

        if (storeStatus != null && totalRating != null) {
            result = shoppingMallRepository.findByStoreStatusAndTotalRating(StoreStatus.of(storeStatus), TotalRating.of(totalRating), pageable);
        } else if (storeStatus != null) {
            result = shoppingMallRepository.findByStoreStatus(StoreStatus.of(storeStatus), pageable);
        } else if (totalRating != null) {
            result = shoppingMallRepository.findByTotalRating(TotalRating.of(totalRating), pageable);
        } else {
            result = shoppingMallRepository.findAll(pageable);
        }

        return result.map(mall -> new ShoppingMallResponse(
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

    @Transactional(readOnly = true)
    public ShoppingMallResponseDto get(Long id) {

        ShoppingMall shoppingMall = shoppingMallRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_SHOPPING_MALL)
        );

        return ShoppingMallResponseDto.of(shoppingMall);
    }

    @Transactional
    public void saveCsvFileDeveloped(String filePath) throws IOException {

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext(); // 헤더
            List<ShoppingMall> batchList = new ArrayList<>(); // 100개씩 담아서 저장할 컬렉션
            String[] line;

            while ((line = reader.readNext()) != null) {
                // 행 전체가 공백이라면 무시하고 다음 행부터 읽음
                boolean isBlankLine = Arrays.stream(line).allMatch(cell -> cell == null || cell.trim().isEmpty());
                if (isBlankLine) {
                    continue;
                }

                ShoppingMall shoppingMall = mapToEntity(line);
                batchList.add(shoppingMall);

                // 100개 저장됐으면 DB에 저장
                if (batchList.size() == BATCH_SIZE) {
                    shoppingMallRepository.saveAll(batchList);
                    batchList.clear(); // 100개 저장했으니 다시 초기화
                }

                // 100개 미만으로 남은 데이터 저장
                if (!batchList.isEmpty()) {
                    shoppingMallRepository.saveAll(batchList);
                }
            }

        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private ShoppingMall mapToEntity(String[] line) {

        // 전체평가 점수가 숫자가 아닐 경우 예외처리
        int ratingValue;
        try {
            ratingValue = Integer.parseInt(line[10]);
        } catch (NumberFormatException e) {
            throw new ApplicationException(ErrorCode.NOT_INT_VALUE_OF_TOTAL_RATING);
        }

        return ShoppingMall.builder()
                .companyName(line[0])
                .storeName(line[1])
                .domainName(line[2])
                .phoneNumber(line[3])
                .operatorEmail(line[4])
                .businessType(line[5])
                .registrationDate(LocalDate.parse(line[7]))
                .companyAddress(line[8])
                .storeStatus(StoreStatus.of(line[9]))
                .totalRating(TotalRating.of(ratingValue))
                .mainProducts(line[16])
                .subscriptionWithdrawalAvailable(line[17])
                .homepageRequiredItems(line[18])
                .termsOfServiceCompliance(line[20])
                .estimateDeliveryDateDisplay(line[26])
                .withdrawalShippingCostResponsibility(line[27])
                .monitoringDate(LocalDate.now())
                .build();
    }
}

