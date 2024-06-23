package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.trade.domain.enums.PeriodType;
import com.sun.fintrack.trade.domain.enums.TradeType;
import com.sun.fintrack.trade.request.TradeEntryRequest;
import com.sun.fintrack.trade.request.TradeModifyRequest;
import com.sun.fintrack.trade.request.TradeMonthlyRequest;
import com.sun.fintrack.trade.request.TradeStatsRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 거래 요청 파라미터 검증
 */
@UtilityClass
public class TradeValidator {

  /**
   * 거래 내역 카테고리별 통계 조회 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(TradeStatsRequest param) {
    // 거래 타입
    validateType(param.getTradeType());
    // 기간 종류 타입
    validateEmpty(param.getPeriodType(), "trade.param_period_type_empty");
    if (!PeriodType.containCode(param.getPeriodType())) {
      throw new ValidationException("trade.param_period_type_invalid");
    }
    PeriodType periodType = PeriodType.fromCode(param.getPeriodType());
    if (periodType == PeriodType.DAILY || periodType == PeriodType.WEEKLY) {
      // 일간/주간 검색 날짜
      validateEmpty(param.getSearchDate(), "trade.param_search_date_empty");
      validateDate(param.getSearchDate());
    } else if (periodType == PeriodType.MONTHLY) {
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
    } else if (periodType == PeriodType.YEARLY) {
      // 연도
      if (Objects.isNull(param.getYear())) {
        throw new ValidationException("trade.param_year_empty");
      }
    } else {
      // 기간 시작 날짜
      validateEmpty(param.getStartDate(), "trade.param_start_date_empty");
      validateDate(param.getStartDate());
      // 기간 종료 날짜
      validateEmpty(param.getEndDate(), "trade.param_end_date_empty");
      validateDate(param.getEndDate());
    }
  }

  /**
   * 거래 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(TradeEntryRequest param) {
    // 거래 내용
    validateEmpty(param.getContent(), "trade.param_content_empty");
    // 거래 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("trade.param_price_empty");
    }
    // 거래일시
    validateEmpty(param.getTradeDt(), "trade.param_trade_dt_empty");
    if (!DateTimeUtils.validFormat(param.getTradeDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 거래 타입
    validateType(param.getType());
    // 자산 일련변호
    if (Objects.isNull(param.getAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 카테고리 일련번호
    if (Objects.isNull(param.getCategorySeq())) {
      throw new ValidationException("trade.param_category_empty");
    }
  }

  /**
   * 거래 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(TradeModifyRequest param) {
    // 거래 일련번호
    if (Objects.isNull(param.getTradeSeq())) {
      throw new ValidationException("trade.param_seq_empty");
    }
    // 거래 내용
    validateEmpty(param.getContent(), "trade.param_content_empty");
    // 거래 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("trade.param_price_empty");
    }
    // 거래일시
    validateEmpty(param.getTradeDt(), "trade.param_trade_dt_empty");
    if (!DateTimeUtils.validFormat(param.getTradeDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 거래 타입
    validateType(param.getType());
    // 자산 일련변호
    if (Objects.isNull(param.getAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 카테고리 일련번호
    if (Objects.isNull(param.getCategorySeq())) {
      throw new ValidationException("trade.param_category_empty");
    }
  }

  /**
   * 월별 거래 내역 조회 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(TradeMonthlyRequest param) {
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
   * 거래 일련번호 & 거래 타입 유효성 체크
   *
   * @param tradeSeq 거래 일련번호
   * @param type     거래 타입
   */
  public void validate(Long tradeSeq, String type) {
    // 거래 일련번호
    validateSeq(tradeSeq);
    // 거래 타입
    validateType(type);
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
   * 거래 일련번호 유효성 체크
   *
   * @param tradeSeq 거래 일련번호
   */
  public void validateSeq(Long tradeSeq) {
    if (Objects.isNull(tradeSeq)) {
      throw new ValidationException("trade.param_seq_empty");
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
