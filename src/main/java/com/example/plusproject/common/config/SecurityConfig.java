package com.example.plusproject.common.config;

import com.example.plusproject.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    // JwtAuthenticationFilter 의존성 주입
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 이제는 따로 PasswordEncoder를 만들 필요 없이, SpringSecurity 제공해주는 것을 쓴다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 간단히 말하면, filter 등록을 하는 것이지만, (addFilterBefore 여기에서)
    // 기존 JwtFilter에서 했던 url 허용을 authorizeHttpRequests 여기에서 해준다.
    // .anyRequest().authenticated() <- 기본적으로 무조건 들어가고,
    // 그 외에 특별한 동작을 하는 url path만 따로 정의를 해주면 된다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, SecurityContextHolderAwareRequestFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                // 이 authorizeHttpRequests 부분만 잘 조작하시고, 이해하시면 됩니다.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(request -> request.getRequestURI().startsWith("/api/v1/auth")).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
