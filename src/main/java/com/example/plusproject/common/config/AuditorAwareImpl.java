package com.example.plusproject.common.config;

import com.example.plusproject.common.dto.AuthUser;
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

    // 인증된 사용자로부터 id 값을 추출하여 CreatedById 또는 ModifiedById 에 값을 넣음
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            return Optional.empty();
        }

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return Optional.of(userService.getUserEntity(authUser.getUserId()));
    }
}
