package com.sun.fintrack.auth.controller;


import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.request.AuthLoginRequest;
import com.sun.fintrack.auth.service.AuthService;
import com.sun.fintrack.auth.validation.AuthValidator;
import com.sun.fintrack.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  /**
   * 로그인 url로 redirect
   */
  @SneakyThrows
  @GetMapping(value = "/login/url")
  public void doGetLoginUrl(HttpServletResponse response) {

    response.sendRedirect(authService.getAuthCodeRequestUrl(SocialType.KAKAO));
  }

  /**
   * 로그인
   *
   * @param param 요청 파라미터
   */
  @PostMapping(value = "/login")
  public ResponseEntity<?> doPostLogin(@RequestBody AuthLoginRequest param) {
    AuthValidator.validate(param);

    authService.login(SocialType.KAKAO, param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
