package com.sun.fintrack.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

/**
 * Security Configuration
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // CSRF 보안 비활성화
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        // 세션을 생성하지 않게 설정
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            (authorize) -> authorize.requestMatchers("/images/**", "/static/**", "/error", "/health", "/test")
                                    .permitAll()
                                    .requestMatchers("/", "/login", "/auth/**")
                                    .anonymous()
                                    .anyRequest()
                                    .denyAll())
        .securityContext((securityContext) -> securityContext.requireExplicitSave(false));

    return http.build();
  }
}
