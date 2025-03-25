package com.example.plusproject.auth.service;

import com.example.plusproject.auth.dto.request.SigninRequestDto;
import com.example.plusproject.auth.dto.request.SignupRequestDto;
import com.example.plusproject.auth.dto.response.AuthResponseDto;
import com.example.plusproject.common.config.JwtUtil;
import com.example.plusproject.user.dto.response.AuthUserResponseDto;
import com.example.plusproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        userService.save(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getUserRole()
        );
    }

    @Transactional
    public AuthResponseDto signin(SigninRequestDto requestDto) {
        AuthUserResponseDto user = userService.findByEmail(requestDto.getEmail(), requestDto.getPassword());
        String bearerJwt = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());
        return new AuthResponseDto(bearerJwt);
    }
}
