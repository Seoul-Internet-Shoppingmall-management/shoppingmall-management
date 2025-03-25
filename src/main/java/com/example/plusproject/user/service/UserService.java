package com.example.plusproject.user.service;

import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.user.dto.UserResponseDto;
import com.example.plusproject.user.entity.User;
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
    public void save(String name, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new ApplicationException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        User user = User.builder()
                .name(name)
                .email(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {


    }
}
