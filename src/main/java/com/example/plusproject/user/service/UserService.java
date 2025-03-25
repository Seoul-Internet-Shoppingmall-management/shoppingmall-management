package com.example.plusproject.user.service;

import com.example.plusproject.common.dto.AuthUser;
import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.user.dto.request.ChangePasswordRequestDto;
import com.example.plusproject.user.dto.request.UserUpdateRequestDto;
import com.example.plusproject.user.dto.response.AuthUserResponseDto;
import com.example.plusproject.user.dto.response.UserResponseDto;
import com.example.plusproject.user.entity.User;
import com.example.plusproject.user.enums.UserRole;
import com.example.plusproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(String name, String email, String password, String userRole) {

        if (userRepository.existsByEmail(email)) {
            throw new ApplicationException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.of(userRole))
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthUserResponseDto findByEmail(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }

        return AuthUserResponseDto.of(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto get(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );

        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto update(AuthUser authUser, UserUpdateRequestDto requestDto) {

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }

        user.update();
        return UserResponseDto.of(user);
    }

    @Transactional
    public void changePassword(AuthUser authUser, ChangePasswordRequestDto requestDto) {

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );

        // 현재 비밀번호가 틀렸을 경우
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }

        // 동일한 비밀번호로 바꾸려는 경우
        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.SELFSAME_PASSWORD);
        }

        user.changePassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }
}
