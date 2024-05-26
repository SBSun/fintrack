package com.sun.fintrack.auth.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 소셜 타입
 */
@Getter
@RequiredArgsConstructor
public enum SocialType {

  /**
   * 카카오
   */
  KAKAO("K");

  private final String code;

}
