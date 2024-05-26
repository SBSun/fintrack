package com.sun.fintrack.auth.request;

import lombok.Getter;

/**
 * 로그인 요청
 */
@Getter
public class AuthLoginRequest {

  /**
   * AuthCode
   */
  private String authCode;
}