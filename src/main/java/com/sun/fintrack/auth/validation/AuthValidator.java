package com.sun.fintrack.auth.validation;

import com.sun.fintrack.common.exception.ValidationException;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * auth 요청 파라미터 검증
 */
@UtilityClass
public class AuthValidator {

  /**
   * 로그인 요청 유효성 체크
   *
   * @param code 인가 코드
   */
  public void validate(String code) {
    if (StringUtils.isBlank(code)) {
      throw new ValidationException("auth.param_auth_code_empty");
    }
  }
}
