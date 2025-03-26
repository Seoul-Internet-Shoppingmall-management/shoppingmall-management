package com.example.plusproject.shoppingmall.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/collection-openapi")
    public ResponseEntity<Map<String, Object>> importOpenApi() {

    }


}
