package com.sun.fintrack.common.config;

import com.sun.fintrack.auth.filter.JwtAuthenticationFilter;
import com.sun.fintrack.auth.service.JwtService;
import com.sun.fintrack.common.config.jwt.JwtProperties;
import com.sun.fintrack.common.exception.handler.CustomAccessDeniedHandler;
import com.sun.fintrack.common.exception.handler.CustomAuthenticationEntryPoint;
import com.sun.fintrack.member.service.MemberOneService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * Security Configuration
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtService jwtService;
  private final JwtProperties jwtProperties;
  private final MemberOneService memberOneService;
  
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
                                    .requestMatchers("/", "/auth/**")
                                    .anonymous()
                                    .anyRequest()
                                    .denyAll())
        .addFilterBefore(new JwtAuthenticationFilter(jwtService, jwtProperties, memberOneService),
            UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(it -> {
          it.authenticationEntryPoint(customAuthenticationEntryPoint);
          it.accessDeniedHandler(customAccessDeniedHandler);
        })
        .securityContext((securityContext) -> securityContext.requireExplicitSave(false));

    return http.build();
  }
}
