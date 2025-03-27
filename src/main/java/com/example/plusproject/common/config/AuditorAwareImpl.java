package com.example.plusproject.common.config;

import com.example.plusproject.common.dto.AuthUser;
import com.example.plusproject.common.exception.ApplicationException;
import com.example.plusproject.common.exception.ErrorCode;
import com.example.plusproject.user.entity.User;
import com.example.plusproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<User> {

    private final UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            throw new ApplicationException(ErrorCode.NOT_FOUND_USER);
        }

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return Optional.of(userService.getUserEntity(authUser.getUserId()));
    }
}
