package com.example.plusproject.shoppingmall.service;

import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.dto.ShoppingMallUpdateRequestDto;
import com.example.plusproject.shoppingmall.dto.response.ShoppingMallResponse;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.repository.ShoppingMallRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ShoppingMallRepository shoppingMallRepository;
    private final ModelMapper modelMapper;

    private static final int BATCH_SIZE = 100;

    @Transactional(readOnly = true)
    public Page<ShoppingMallResponse> getShoppingMalls(String storeStatus, Integer totalRating, int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

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
        Page<ShoppingMallResponse> responsePage = result.map(mall -> new ShoppingMallResponse(
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
                mall.getCreatedBy().getId(),
                mall.getModifiedBy().getId()
        ));
        return responsePage;
        }

    @Transactional(readOnly = true)
    public ShoppingMallResponseDto get(Long id) {

        ShoppingMall shoppingMall = shoppingMallRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_SHOPPING_MALL)
        );

        return ShoppingMallResponseDto.of(shoppingMall);
    }

    @Transactional
    public void update(Long id, ShoppingMallUpdateRequestDto requestDto) {

        ShoppingMall shoppingMall = shoppingMallRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_SHOPPING_MALL)
        );

        // modelMapper 이용한 자동 변환 (null 무시)
        modelMapper.map(requestDto, shoppingMall);

        // Enum 타입은 수동으로
        if (requestDto.getStoreStatus() != null) {
            shoppingMall.changeStoreStatus(StoreStatus.of(requestDto.getStoreStatus()));
        }
        if (requestDto.getTotalRating() != null) {
            shoppingMall.changeTotalRating(TotalRating.of(requestDto.getTotalRating()));
        }
        // 모니터링일자 갱신
        shoppingMall.changeMonitoringDate(LocalDate.now());
    }

    /*---------------------------------------------- Open API ----------------------------------------------------------*/

    // 서울 열린 데이터 광장 인증키 (환경 변수로 인증키 넣어야 함)
    @Value("${openapi.seoul.serviceKey}")
    private String serviceKey;

    @Transactional
    public int importAllOpenApiData() {

        // 총 데이터 건수 확인용 URL 설정
        String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/ServiceInternetShopInfo/1/1", serviceKey);

        // OpenAPI 호출
        String response = restTemplate.getForObject(url, String.class);

        // 응답이 null일 경우 호출 실패
        if (response == null) {
            throw new RuntimeException("OpenAPI 응답이 없습니다.");
        }

        int totalCount;
        try {

            // 응답 문자열을 JSON 트리 구조로 변환
            JsonNode root = objectMapper.readTree(response);

            // JSON에 RESULT 노드의 하위 CODE 값 확인 -> INFO-000 이면 정상 처리 그 외에는 오류
            if (root.has("RESULT") && !"INFO-000".equals(root.path("RESULT").path("CODE").asText())) {
                throw new RuntimeException("OpenAPI 호출 실패: " + root.path("RESULT").path("MESSAGE").asText());
            }

            // JSON에서 ServiceInternetShopInfo 노드의 list_total_count를 int로 totalCount(총 데이터 건수)에 저장
            totalCount = root.path("ServiceInternetShopInfo").path("list_total_count").asInt();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }

        int pageSize = 1000;     // 한번에 가져올 데이터 크기
        int totalInserted = 0;  // DB에 삽입된 총 행 수

        // 총 데이터 갯수만큼 반복해서 데이터 삽입
        for (int start = 1; start <= totalCount; start += pageSize) {
            int end = Math.min(start + pageSize - 1, totalCount);
            totalInserted += importOpenApiData(start, end);
        }

        return totalInserted;   // 총 삽입된 행 수
    }

    public int importOpenApiData(int start, int end) {

        // URL 생성 start ~ end 범위 요청
        String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/ServiceInternetShopInfo/%d/%d", serviceKey, start, end);

        // RestTemplate으로 url 호출하여 JSON 응답을 문자열로 받음
        String response = restTemplate.getForObject(url, String.class);

        // 응답이 null일 경우 호출 실패
        if (response == null) {
            throw new RuntimeException("OpenAPI 응답이 없습니다.");
        }

        try {
            // 응답 문자열을 JSON 트리 구조로 변환
            JsonNode root = objectMapper.readTree(response);

            // JSON에 RESULT 노드의 하위 CODE 값 확인 -> INFO-000 이면 정상 처리 그 외에는 오류
            if (root.has("RESULT") && !"INFO-000".equals(root.path("RESULT").path("CODE").asText())) {
                throw new RuntimeException("OpenAPI 호출 실패: " + root.path("RESULT").path("MESSAGE").asText());
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }

        // JSON 파싱하여 Store 객체 리스트로 변환
        List<ShoppingMall> shoppingMalls = parseJson(response);

        // Store 객체 리스트 DB 저장
        shoppingMallRepository.saveAll(shoppingMalls);

        // 저장된 store 객체의 갯수 반환
        return shoppingMalls.size();
    }

    // JSON 응답을 파싱해 Store 객체 리스트로 변환하는 메서드
    private List<ShoppingMall> parseJson(String response) {

        try {
            // 응답 문자열을 JSON 트리 구조로 변환
            JsonNode root = objectMapper.readTree(response);
            // ServiceInternetShopInfo 노드의 row 배열을 추출 <-실제 데이터가 들어 있는 곳
            JsonNode rows = root.path("ServiceInternetShopInfo").path("row");
            // Store 객체를 저장할 ArrayList
            List<ShoppingMall> shoppingMalls = new ArrayList<>();

            for (JsonNode row : rows) {
                // Store 객체 생성
                ShoppingMall shoppingMall = new ShoppingMall(
                        row.path("COMPANY").asText(null),
                        row.path("SHOP_NAME").asText(null),
                        row.path("DOMAIN_NAME").asText(null),
                        row.path("TEL").asText(null),
                        row.path("EMAIL").asText(null),
                        row.path("YPFORM").asText(null),
                        row.path("FIRST_HEO_DATE").asText(null) != null ? LocalDate.parse(row.path("FIRST_HEO_DATE").asText(null)) : null,
                        row.path("COM_ADDR").asText(null),
                        row.path("STAT_NM").asText(null) != null ? StoreStatus.of(row.path("STAT_NM").asText(null)) : StoreStatus.NONE,
                        TotalRating.of(row.path("TOT_RATINGPOINT").asInt(0)),
                        row.path("SERVICE").asText(null),
                        row.path("CHUNG").asText(null),
                        row.path("CHOGI").asText(null),
                        row.path("PYOJUN").asText(null),
                        row.path("BAESONG_YEJEONG").asText(null),
                        row.path("BAESONG").asText(null),
                        row.path("REG_DATE").asText(null) != null ? LocalDate.parse(row.path("REG_DATE").asText(null)) : null);

                shoppingMalls.add(shoppingMall);
            }

            return shoppingMalls;

        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
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

    @Transactional
    public void saveCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // 첫 번째 줄은 헤더이므로 건너뜀
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length != 17) continue; // 필드 개수 확인

                ShoppingMall shoppingMall = new ShoppingMall(
                        data[0],  // companyName
                        data[1],  // storeName
                        data[2],  // domainName
                        data[3],  // phoneNumber
                        data[4],  // operatorEmail
                        data[5],  // businessType
                        LocalDate.parse(data[6]),  // registrationDate
                        data[7],  // companyAddress
                        StoreStatus.valueOf(data[8]),  // storeStatus
                        TotalRating.valueOf(data[9]),  // totalRating
                        data[10], // mainProducts
                        data[11], // subscriptionWithdrawalAvailable
                        data[12], // homepageRequiredItems
                        data[13], // termsOfServiceCompliance
                        data[14], // estimateDeliveryDateDisplay
                        data[15], // withdrawalShippingCostResponsibility
                        LocalDate.parse(data[16])  // monitoringDate
                );

                shoppingMallRepository.save(shoppingMall);
            }
        } catch (IOException e) {
            throw new RuntimeException("CSV 파일 처리 중 오류 발생", e);
        }
    }
}
