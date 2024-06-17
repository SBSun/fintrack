package com.sun.fintrack.controller;


import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.response.AuthStatusResponse;
import com.sun.fintrack.auth.service.AuthService;
import com.sun.fintrack.common.config.jwt.JwtProperties;
import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.utils.CookieUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.validation.AuthValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

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
  public void doGetAuthKakao(@RequestParam(required = false) String code, HttpServletResponse response) {
    AuthValidator.validate(code);

    CookieUtils.setCookie(jwtProperties.cookieName(), authService.login(SocialType.KAKAO, code), 24 * 60 * 60,
        response);

    try {
      response.sendRedirect("http://localhost:3000");
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  /**
   * 로그인 여부 반환
   */
  @GetMapping(value = "/status")
  public ResponseEntity<?> doGetAuthStatus() {
    return ResponseEntity.ok(new DataResponse(new AuthStatusResponse(Objects.nonNull(MemberUtils.getMember()))));
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
