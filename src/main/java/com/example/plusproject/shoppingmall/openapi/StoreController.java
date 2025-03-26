package com.example.plusproject.shoppingmall.openapi;

import com.example.plusproject.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StoreController {

    private final StoreService storeService;

    @Secured(UserRole.Authority.ADMIN)
    @PostMapping("/collection-openapi")
    public ResponseEntity<ImportResponseDto> importOpenApi() {

        int insertedRows = storeService.importOpenApiData(1, 100);

        return ResponseEntity.ok(new ImportResponseDto("데이터 입력 완료", insertedRows));
    }


}
