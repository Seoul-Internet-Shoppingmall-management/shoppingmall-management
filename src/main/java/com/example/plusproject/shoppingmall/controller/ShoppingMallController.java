package com.example.plusproject.shoppingmall.controller;

import com.example.plusproject.filter.dto.ShoppingMallResponseDto;
import com.example.plusproject.filter.dto.ShoppingMallUpdateRequestDto;
import com.example.plusproject.shoppingmall.dto.response.ShoppingMallResponse;
import com.example.plusproject.shoppingmall.entity.ShoppingMall;
import com.example.plusproject.shoppingmall.enums.StoreStatus;
import com.example.plusproject.shoppingmall.enums.TotalRating;
import com.example.plusproject.shoppingmall.service.ShoppingMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    // csv 파일 내 데이터를 100개씩 읽어오는 메서드
    @PostMapping("/v1/shopping-malls")
    public ResponseEntity<Void> uploadCsvFileDeveloped(
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            throw new ApplicationException(ErrorCode.EMPTY_FILE);
        }

        try {
            // 파일을 임시 디렉토리에 저장
            File tempFile = File.createTempFile("uploaded", ".csv");
            file.transferTo(tempFile);

            shoppingMallService.saveCsvFileDeveloped(tempFile.getAbsolutePath());

            // 임시 파일 삭제
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/shopping-malls/{id}")
    public ResponseEntity<ShoppingMallResponseDto> get(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(shoppingMallService.get(id));
    }
    // 쇼핑몰을 페이징해서 조회(필터 적용 가능)
    @GetMapping("/api/v1/shopping-malls")
    public ResponseEntity<Page<ShoppingMallResponse>> getShoppingMalls(
            @RequestParam(required = false) StoreStatus storeStatus,
            @RequestParam(required = false) TotalRating totalRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        return ResponseEntity.ok(shoppingMallService.getShoppingMalls(storeStatus, totalRating, page, size));
    }

}
