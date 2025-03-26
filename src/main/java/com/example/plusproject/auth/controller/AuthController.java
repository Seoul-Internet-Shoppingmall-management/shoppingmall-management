package com.example.plusproject.auth.controller;

import com.example.plusproject.auth.dto.request.SigninRequestDto;
import com.example.plusproject.auth.dto.request.SignupRequestDto;
import com.example.plusproject.auth.dto.response.AuthResponseDto;
import com.example.plusproject.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<AuthResponseDto> signin(@Valid @RequestBody SigninRequestDto requestDto) {
        return ResponseEntity.ok(authService.signin(requestDto));
    }
}
