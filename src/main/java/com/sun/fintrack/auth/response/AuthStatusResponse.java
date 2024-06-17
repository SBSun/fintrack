package com.sun.fintrack.auth.response;

import lombok.Getter;

/**
 * 로그인 상태 반환
 */
@Getter
public class AuthStatusResponse {
  /**
   * 로그인 상태
   */
  private final Boolean isAuthenticated;

  public AuthStatusResponse(Boolean isAuthenticated) {
    this.isAuthenticated = isAuthenticated;
  }
}
