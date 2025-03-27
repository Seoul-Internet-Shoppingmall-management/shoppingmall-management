package com.example.plusproject.user.service;

import com.example.plusproject.common.dto.AuthUser;
import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.user.dto.request.ChangePasswordRequestDto;
import com.example.plusproject.user.dto.request.UserPasswordRequestDto;
import com.example.plusproject.user.dto.request.UserUpdateRequestDto;
import com.example.plusproject.user.dto.response.AuthUserResponseDto;
import com.example.plusproject.user.dto.response.UserResponseDto;
import com.example.plusproject.user.entity.User;
import com.example.plusproject.user.enums.UserRole;
import com.example.plusproject.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        // 탈퇴된 사용자의 경우 로그인 불가
        if (user.getDeletedAt() != null) {
            throw new ApplicationException(ErrorCode.DELETED_USER);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }

        return AuthUserResponseDto.of(user);
    }

    @Transactional
    public void restore(Long userId, @Valid UserPasswordRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );

        // 현재 비밀번호가 틀렸을 경우
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }

        // 탈퇴되지 않은 사용자인 경우
        if (user.getDeletedAt() == null) {
            throw new ApplicationException(ErrorCode.UNDELETED_USER);
        }

        user.formDeletedAt(null);
    }

    @Transactional(readOnly = true)
    public UserResponseDto get(Long userId) {

        // SoftDelete Filter 활성화
        userRepository.enableSoftDeleteFilter();

        User user = userRepository.findByIdWithFilter(userId).orElseThrow(
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

        user.update(requestDto.getName());
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

    @Transactional
    public void delete(AuthUser authUser, @Valid UserPasswordRequestDto requestDto) {

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );

        // 현재 비밀번호가 틀렸을 경우
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }

        // 이미 탈퇴된 사용자인 경우
        if (user.getDeletedAt() != null) {
            throw new ApplicationException(ErrorCode.DELETED_USER);
        }

        user.formDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public User getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_USER)
        );
    }
}
