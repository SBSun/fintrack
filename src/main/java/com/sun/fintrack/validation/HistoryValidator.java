package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 거래 요청 파라미터 검증
 */
@UtilityClass
public class HistoryValidator {

  /**
   * 월별 거래 내역 조회 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(HistoryMonthlyRequest param) {
    // 연도
    if (Objects.isNull(param.getYear())) {
      throw new ValidationException("trade.param_year_empty");
    }
    // 월
    Integer month = param.getMonth();
    if (Objects.isNull(month)) {
      throw new ValidationException("trade.param_month_empty");
    }
    if (month < 1 || month > 12) {
      throw new ValidationException("trade.param_month_invalid");
    }
  }

  /**
   * 일일 거래 내역 조회 유효성 체크
   *
   * @param date 날짜
   */
  public void validateDaily(String date) {
    // 날짜
    validateEmpty(date, "trade.param_date_empty");
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
   * 카테고리 타입 유효성 체크
   *
   * @param type 카테고리 타입
   */
  public void validateType(String type) {
    validateEmpty(type, "trade.param_type_empty");
    if (!TradeType.containCode(type)) {
      throw new ValidationException("trade.param_type_invalid");
    }
  }

  private void validateDate(String date) {
    if (!DateTimeUtils.validFormat(date, DateTimeUtils.DEFAULT_DATE)) {
      throw new ValidationException("date.param_date_invalid");
    }
  }
}
