package com.example.plusproject.user.controller;

import com.example.plusproject.common.dto.AuthUser;
import com.example.plusproject.user.dto.request.ChangePasswordRequestDto;
import com.example.plusproject.user.dto.request.UserUpdateRequestDto;
import com.example.plusproject.user.dto.response.UserResponseDto;
import com.example.plusproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> get(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.get(userId));
    }

    @PatchMapping("/users")
    public ResponseEntity<UserResponseDto> update(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.update(authUser, requestDto));
    }

    @PutMapping("/users/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ChangePasswordRequestDto requestDto
    ) {
        userService.changePassword(authUser, requestDto);
        return ResponseEntity.ok().build();
    }
}
