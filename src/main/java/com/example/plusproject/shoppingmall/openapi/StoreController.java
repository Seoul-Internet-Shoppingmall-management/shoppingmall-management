package com.example.plusproject.shoppingmall.openapi;

import com.example.plusproject.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    @Secured(UserRole.Authority.ADMIN)
    @PostMapping("/v1/collection-openapi")
    public ResponseEntity<ImportResponseDto> importOpenApi() {

        int insertedRows = storeService.importAllOpenApiData();

        return ResponseEntity.ok(new ImportResponseDto("데이터 입력 완료", insertedRows));
    }


}
