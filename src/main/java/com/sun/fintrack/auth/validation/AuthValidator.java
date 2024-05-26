package com.sun.fintrack.auth.validation;

import com.sun.fintrack.auth.request.AuthLoginRequest;
import com.sun.fintrack.common.exception.ValidationException;

import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * auth 요청 파라미터 검증
 */
@UtilityClass
public class AuthValidator {

  /**
   * 로그인 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(AuthLoginRequest param) {
    if (!StringUtils.hasText(param.getAuthCode())) {
      throw new ValidationException("auth.param_auth_code_empty");
    }
  }
}
