package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.income.request.IncomeEntryRequest;
import com.sun.fintrack.income.request.IncomeModifyRequest;
import com.sun.fintrack.income.request.IncomeStatsRequest;
import com.sun.fintrack.payment.domain.enums.PeriodType;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 소득 요청 파라미터 검증
 */
@UtilityClass
public class IncomeValidator {

  /**
   * 소득 내역 카테고리별 통계 조회 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(IncomeStatsRequest param) {
    // 기간 종류 타입
    validateEmpty(param.getPeriodType(), "income.param_period_type_empty");
    if (!PeriodType.containCode(param.getPeriodType())) {
      throw new ValidationException("income.param_period_type_invalid");
    }
    PeriodType periodType = PeriodType.fromCode(param.getPeriodType());
    if (periodType == PeriodType.DAILY || periodType == PeriodType.WEEKLY) {
      // 일간/주간 검색 날짜
      validateEmpty(param.getSearchDate(), "income.param_search_date_empty");
      validateDate(param.getSearchDate());
    } else if (periodType == PeriodType.MONTHLY) {
      // 연도
      if (Objects.isNull(param.getYear())) {
        throw new ValidationException("income.param_year_empty");
      }
      // 월
      Integer month = param.getMonth();
      if (Objects.isNull(month)) {
        throw new ValidationException("income.param_month_empty");
      }
      if (month < 1 || month > 12) {
        throw new ValidationException("income.param_month_invalid");
      }
    } else if (periodType == PeriodType.YEARLY) {
      // 연도
      if (Objects.isNull(param.getYear())) {
        throw new ValidationException("income.param_year_empty");
      }
    } else {
      // 기간 시작 날짜
      validateEmpty(param.getStartDate(), "income.param_start_date_empty");
      validateDate(param.getStartDate());
      // 기간 종료 날짜
      validateEmpty(param.getEndDate(), "income.param_end_date_empty");
      validateDate(param.getEndDate());
    }
  }

  /**
   * 소득 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(IncomeEntryRequest param) {
    // 소득 내용
    validateEmpty(param.getContent(), "income.param_content_empty");
    // 소득 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("income.param_price_empty");
    }
    // 소득일시
    validateEmpty(param.getIncomeDt(), "income.param_income_dt_empty");
    if (!DateTimeUtils.validFormat(param.getIncomeDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 자산 일련변호
    if (Objects.isNull(param.getAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 카테고리 일련번호
    if (Objects.isNull(param.getCategorySeq())) {
      throw new ValidationException("income.param_category_empty");
    }
  }

  /**
   * 소득 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(IncomeModifyRequest param) {
    // 소득 일련번호
    if (Objects.isNull(param.getIncomeSeq())) {
      throw new ValidationException("income.param_seq_empty");
    }
    // 소득 내용
    validateEmpty(param.getContent(), "income.param_content_empty");
    // 소득 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("income.param_price_empty");
    }
    // 소득일시
    validateEmpty(param.getIncomeDt(), "income.param_income_dt_empty");
    if (!DateTimeUtils.validFormat(param.getIncomeDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 자산 일련변호
    if (Objects.isNull(param.getAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 카테고리 일련번호
    if (Objects.isNull(param.getCategorySeq())) {
      throw new ValidationException("income.param_category_empty");
    }
  }

  /**
   * 일일 소득 내역 목록 조회 유효성 체크
   *
   * @param date 날짜
   */
  public void validateDaily(String date) {
    validateEmpty(date, "income.param_date_empty");
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
   * 월별 소득 내역 목록 조회 유효성 체크
   *
   * @param year  연도
   * @param month 월
   */
  public void validateMonthly(Integer year, Integer month) {
    if (Objects.isNull(year)) {
      throw new ValidationException("income.param_year_empty");
    }
    if (Objects.isNull(month)) {
      throw new ValidationException("income.param_month_empty");
    }
    if (month < 1 || month > 12) {
      throw new ValidationException("income.param_month_invalid");
    }
  }

  /**
   * 소득 일련번호 유효성 체크
   *
   * @param incomeSeq 소득 일련번호
   */
  public void validateSeq(Long incomeSeq) {
    if (Objects.isNull(incomeSeq)) {
      throw new ValidationException("income.param_seq_empty");
    }
  }

  private void validateDate(String date) {
    if (!DateTimeUtils.validFormat(date, DateTimeUtils.DEFAULT_DATE)) {
      throw new ValidationException("date.param_date_invalid");
    }
  }
}
