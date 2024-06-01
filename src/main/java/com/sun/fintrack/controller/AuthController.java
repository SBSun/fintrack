package com.sun.fintrack.controller;


import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.service.AuthService;
import com.sun.fintrack.common.config.jwt.JwtProperties;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.common.utils.CookieUtils;
import com.sun.fintrack.validation.AuthValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

  private final AuthService authService;
  private final JwtProperties jwtProperties;

  /**
   * 로그인
   *
   * @param code 인가 코드
   */
  @GetMapping(value = "/kakao")
  public ResponseEntity<?> doGetAuthKakao(@RequestParam(required = false) String code, HttpServletResponse response) {
    AuthValidator.validate(code);

    CookieUtils.setCookie(jwtProperties.cookieName(), authService.login(SocialType.KAKAO, code), 24 * 60 * 60,
        response);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 로그인 url로 redirect
   */
  @SneakyThrows
  @GetMapping(value = "/login/url")
  public void doGetLoginUrl(HttpServletResponse response) {
    response.sendRedirect(authService.getAuthCodeRequestUrl(SocialType.KAKAO));
  }
}
