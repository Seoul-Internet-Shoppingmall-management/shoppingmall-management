package com.example.plusproject.shoppingmall.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    public int importOpenApiData(int start, int end) {

        // URL 설정
        String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/ServiceInternetShopInfo/%d/%d", serviceKey, start, end);

        // OpenAPI 호출
        String response = restTemplate.getForObject(url, String.class);
        if (response == null) {
            throw new RuntimeException("OpenAPI 응답이 없습니다.");
        }

        // JSON 파싱
        List<Store> stores = parseJson(response);

        // DB 저장
        storeRepository.saveAll(stores);
        return stores.size();
    }

    private List<Store> parseJson(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode rows = root.path("ServiceInternetShopInfo").path("row");
            List<Store> stores = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            for (JsonNode row : rows) {
                Store store = new Store();
                store.setCompanyName(row.path("COMPANY").asText(null));
                store.setStoreName(row.path("SHOP_NAME").asText(null));
                store.setDomainName(row.path("DOMAIN_NAME").asText(null));
                store.setPhoneNumber(row.path("TEL").asText(null));
                store.setOperatorEmail(row.path("EMAIL").asText(null));
                store.setBusinessType(row.path("YPFORM").asText(null));
                String regDateStr = row.path("FIRST_HEO_DATE").asText(null);
                if (regDateStr != null) {
                    store.setRegistrationDate(LocalDateTime.parse(regDateStr, formatter));
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
                if (monitorDateStr != null) {
                    store.setMonitoringDate(LocalDateTime.parse(monitorDateStr, formatter));
                }
                if (storeRepository.existsByStoreName(store.getStoreName())) {
                    stores.add(store);
                }
            }
            return stores;
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }
}
