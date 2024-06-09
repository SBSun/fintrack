package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.payment.request.PaymentEntryRequest;
import com.sun.fintrack.payment.request.PaymentModifyRequest;

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

  /**
   * 결제 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(PaymentModifyRequest param) {
    if (Objects.isNull(param.getPaymentSeq())) {
      throw new ValidationException("payment.param_payment_seq_empty");
    }
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

  /**
   * 일일 결제 내역 목록 조회 유효성 체크
   *
   * @param date 날짜
   */
  public void validateDaily(String date) {
    if (StringUtils.isBlank(date)) {
      throw new ValidationException("payment.param_date_empty");
    }
    if (!DateTimeUtils.validFormat(date, DateTimeUtils.DEFAULT_DATE)) {
      throw new ValidationException("payment.param_date_invalid");
    }
  }

  /**
   * 문자열 공백 체크
   *
   * @param str 문자열
   */
  public void validateEmpty(String str, String exception) {
    if (StringUtils.isBlank(str)) {
      throw new ValidationException(exception);
    }
  }

  /**
   * 월별 결제 내역 목록 조회 유효성 체크
   *
   * @param year  연도
   * @param month 월
   */
  public void validateMonthly(Integer year, Integer month) {
    if (Objects.isNull(year)) {
      throw new ValidationException("payment.param_year_empty");
    }
    if (Objects.isNull(month)) {
      throw new ValidationException("payment.param_month_empty");
    }
    if (month < 1 || month > 12) {
      throw new ValidationException("payment.param_month_invalid");
    }
  }

  /**
   * 결제 일련번호 유효성 체크
   *
   * @param paymentSeq 결제 일련번호
   */
  public void validateSeq(Long paymentSeq) {
    if (Objects.isNull(paymentSeq)) {
      throw new ValidationException("payment.param_payment_seq_empty");
    }
  }
}
