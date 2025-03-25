package com.example.plusproject.auth.service;

import com.example.plusproject.auth.dto.request.SigninRequestDto;
import com.example.plusproject.auth.dto.request.SignupRequestDto;
import com.example.plusproject.auth.dto.response.AuthResponseDto;
import com.example.plusproject.user.dto.UserResponseDto;
import com.example.plusproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        userService.save(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
    }

    @Transactional
    public AuthResponseDto signin(SigninRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.findByEmail(requestDto.getEmail());
    }
}
