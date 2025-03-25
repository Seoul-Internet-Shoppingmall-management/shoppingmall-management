package com.example.plusproject.user.controller;

import com.example.plusproject.common.dto.AuthUser;
import com.example.plusproject.user.dto.request.ChangePasswordRequestDto;
import com.example.plusproject.user.dto.request.UserPasswordRequestDto;
import com.example.plusproject.user.dto.request.UserUpdateRequestDto;
import com.example.plusproject.user.dto.response.UserResponseDto;
import com.example.plusproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/restore/{userId}")
    public ResponseEntity<Void> restore(
            @PathVariable Long userId,
            @Valid @RequestBody UserPasswordRequestDto requestDto
    ) {
        userService.restore(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> get(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.get(userId));
    }

    @PatchMapping("/users")
    public ResponseEntity<UserResponseDto> update(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.update(authUser, requestDto));
    }

    @PutMapping("/users/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody ChangePasswordRequestDto requestDto
    ) {
        userService.changePassword(authUser, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserPasswordRequestDto requestDto
    ) {
        userService.delete(authUser, requestDto);
        return ResponseEntity.ok().build();
    }
}
