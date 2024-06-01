package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.payment.request.PaymentEntryRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 결제 요청 파라미터 검증
 */
@UtilityClass
public class PaymentValidator {

  /**
   * 결제 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(PaymentEntryRequest param) {
    if (StringUtils.isBlank(param.getContent())) {
      throw new ValidationException("payment.param_content_empty");
    }
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("payment.param_price_empty");
    }
    if (Objects.isNull(param.getCategoryId())) {
      throw new ValidationException("payment.param_category_empty");
    }
  }
}
