package com.example.plusproject.shoppingmall.controller;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.dto.ShoppingMallUpdateRequestDto;
import com.example.plusproject.shoppingmall.dto.ImportResponseDto;
import com.example.plusproject.shoppingmall.service.ShoppingMallService;
import com.example.plusproject.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    @GetMapping("/v1/shopping-malls/{id}")
    public ResponseEntity<ShoppingMallResponseDto> get(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(shoppingMallService.get(id));
    }

    @PatchMapping("/v1/shopping-malls/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody ShoppingMallUpdateRequestDto requestDto
            ) {
        shoppingMallService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    /*-------------------------------------------- Open API ----------------------------------------------------------*/

    @Secured(UserRole.Authority.ADMIN)
    @PostMapping("/v1/collection-openapi")
    public ResponseEntity<ImportResponseDto> importOpenApi() {

        int insertedRows = shoppingMallService.importAllOpenApiData();

        return ResponseEntity.ok(new ImportResponseDto("데이터 입력 완료", insertedRows));
    }
}
