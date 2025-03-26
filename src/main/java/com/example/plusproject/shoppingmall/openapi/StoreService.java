package com.example.plusproject.shoppingmall.openapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;

    public int importOpenApiData(int start, int end) {

    }
}
