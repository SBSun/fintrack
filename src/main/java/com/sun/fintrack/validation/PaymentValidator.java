package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.payment.domain.enums.PeriodType;
import com.sun.fintrack.payment.request.PaymentEntryRequest;
import com.sun.fintrack.payment.request.PaymentModifyRequest;
import com.sun.fintrack.payment.request.PaymentStatsRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 결제 요청 파라미터 검증
 */
@UtilityClass
public class PaymentValidator {

  /**
   * 결제 내역 카테고리별 통계 조회 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(PaymentStatsRequest param) {
    // 기간 종류 타입
    validateEmpty(param.getPeriodType(), "payment.param_period_type_empty");
    if (!PeriodType.containCode(param.getPeriodType())) {
      throw new ValidationException("payment.param_period_type_invalid");
    }
    PeriodType periodType = PeriodType.fromCode(param.getPeriodType());
    if (periodType == PeriodType.DAILY || periodType == PeriodType.WEEKLY) {
      // 일간/주간 검색 날짜
      validateEmpty(param.getSearchDate(), "payment.param_search_date_empty");
      validateDate(param.getSearchDate());
    } else if (periodType == PeriodType.MONTHLY) {
      // 연도
      if (Objects.isNull(param.getYear())) {
        throw new ValidationException("payment.param_year_empty");
      }
      // 월
      Integer month = param.getMonth();
      if (Objects.isNull(month)) {
        throw new ValidationException("payment.param_month_empty");
      }
      if (month < 1 || month > 12) {
        throw new ValidationException("payment.param_month_invalid");
      }
    } else if (periodType == PeriodType.YEARLY) {
      // 연도
      if (Objects.isNull(param.getYear())) {
        throw new ValidationException("payment.param_year_empty");
      }
    } else {
      // 기간 시작 날짜
      validateEmpty(param.getStartDate(), "payment.param_start_date_empty");
      validateDate(param.getStartDate());
      // 기간 종료 날짜
      validateEmpty(param.getEndDate(), "payment.param_end_date_empty");
      validateDate(param.getEndDate());
    }
  }

  /**
   * 결제 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(PaymentEntryRequest param) {
    // 결제 내용
    validateEmpty(param.getContent(), "payment.param_content_empty");
    // 결제 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("payment.param_price_empty");
    }
    // 결제일시
    validateEmpty(param.getPaymentDt(), "payment.param_payment_dt_empty");
    if (!DateTimeUtils.validFormat(param.getPaymentDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 자산 일련변호
    if (Objects.isNull(param.getAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 결제 카테고리 ID
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
    // 결제 일련번호
    if (Objects.isNull(param.getPaymentSeq())) {
      throw new ValidationException("payment.param_payment_seq_empty");
    }
    // 결제 내용
    validateEmpty(param.getContent(), "payment.param_content_empty");
    // 결제 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("payment.param_price_empty");
    }
    // 결제일시
    validateEmpty(param.getPaymentDt(), "payment.param_payment_dt_empty");
    if (!DateTimeUtils.validFormat(param.getPaymentDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 자산 일련변호
    if (Objects.isNull(param.getAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 결제 카테고리 ID
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
    validateEmpty(date, "payment.param_date_empty");
    if (!DateTimeUtils.validFormat(date, DateTimeUtils.DEFAULT_DATE)) {
      throw new ValidationException("date.param_date_invalid");
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

  private void validateDate(String date) {
    if (!DateTimeUtils.validFormat(date, DateTimeUtils.DEFAULT_DATE)) {
      throw new ValidationException("date.param_date_invalid");
    }
  }
}
