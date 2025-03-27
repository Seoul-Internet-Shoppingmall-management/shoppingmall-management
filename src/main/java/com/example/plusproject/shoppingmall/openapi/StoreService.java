package com.example.plusproject.shoppingmall.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;

    @Value("${openapi.seoul.serviceKey}")
    private String serviceKey;

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

        int pageSize = 100;     // 한번에 가져올 데이터 크기
        int totalInserted = 0;  // DB에 삽입된 총 행 수

        // 총 데이터 갯수만큼 반복해서 데이터 삽입
        for (int start = 1; start <= totalCount; start += pageSize) {
            int end = Math.min(start + pageSize - 1, totalCount);
            totalInserted += importOpenApiData(start, end);
        }

        return totalInserted;   // 총 삽입된 행 수
    }

    @Transactional
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
        List<Store> stores = parseJson(response);

        // Store 객체 리스트 DB 저장
        storeRepository.saveAll(stores);

        // 저장된 store 객체의 갯수 반환
        return stores.size();
    }

    // JSON 응답을 파싱해 Store 객체 리스트로 변환하는 메서드
    private List<Store> parseJson(String response) {

        try {
            // 응답 문자열을 JSON 트리 구조로 변환
            JsonNode root = objectMapper.readTree(response);
            // ServiceInternetShopInfo 노드의 row 배열을 추출 <-실제 데이터가 들어 있는 곳
            JsonNode rows = root.path("ServiceInternetShopInfo").path("row");
            // Store 객체를 저장할 ArrayList
            List<Store> stores = new ArrayList<>();

            for (JsonNode row : rows) {
                // Store 객체 생성
                Store store = new Store();

                // OpenApi에서 받은 값을 문자열로 추출해서 받음, 값이 없으면 null 반환
                store.setCompanyName(row.path("COMPANY").asText(null));
                store.setStoreName(row.path("SHOP_NAME").asText(null));
                store.setDomainName(row.path("DOMAIN_NAME").asText(null));
                store.setPhoneNumber(row.path("TEL").asText(null));
                store.setOperatorEmail(row.path("EMAIL").asText(null));
                store.setBusinessType(row.path("YPFORM").asText(null));
                String regDateStr = row.path("FIRST_HEO_DATE").asText(null);
                // regDateStr이 null이 아니면 yyyy-MM-dd 형식으로 파싱해 등록 날짜로 설정
                if (regDateStr != null) {
                    store.setRegistrationDate(LocalDate.parse(regDateStr).atStartOfDay());
                }
                store.setCompanyAddress(row.path("COM_ADDR").asText(null));
                store.setStoreStatus(row.path("STAT_NM").asText(null));
                store.setTotalRating(row.path("TOT_RATINGPOINT").asInt(0));
                store.setMainProducts(row.path("SERVICE").asText(null));
                store.setSubscriptionWithdrawalAvailable(row.path("CHUNG").asText(null));
                store.setHomepageRequiredItems(row.path("CHOGI").asText(null));
                store.setTermsOfServiceCompliance(row.path("PYOJUN").asText(null));
                store.setEstimateDeliveryDateDisplay(row.path("BAESONG").asText(null));
                String monitorDateStr = row.path("REG_DATE").asText(null);
                // monitorDateStr이 null이 아니면 yyyy-MM-dd 형식으로 파싱해 등록 날짜로 설정
                if (monitorDateStr != null) {
                    store.setMonitoringDate(LocalDate.parse(monitorDateStr).atStartOfDay());
                }

                // 중복된 쇼핑몰 데이터를 방지하기 위해 storeName으로 DB 중복 여부를 확인 후 추가
                if (!storeRepository.existsByStoreName(store.getStoreName())) {
                    stores.add(store);
                }
            }

            return stores;

        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }
}
