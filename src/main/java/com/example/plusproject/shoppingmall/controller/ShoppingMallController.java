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
