package com.sun.fintrack.payment.domain.enums;

import com.sun.fintrack.common.domain.enums.Codable;

import java.util.Arrays;

import lombok.Getter;

/**
 * 기간 종류 타입
 */
@Getter
public enum PeriodType implements Codable {

  /**
   * 일간
   */
  DAILY("daily"),
  /**
   * 주간
   */
  WEEKLY("weekly"),
  /**
   * 월간
   */
  MONTHLY("monthly"),
  /**
   * 연간
   */
  YEARLY("yearly"),
  /**
   * 커스텀 기간
   */
  PERIOD("period");

  private final String code;

  PeriodType(String code) {
    this.code = code;
  }

  public static boolean containCode(String code) {
    return Arrays.stream(PeriodType.values()).anyMatch(type -> type.code.equals(code));
  }

  public static PeriodType fromCode(String code) {
    return Codable.fromCode(PeriodType.class, code);
  }
}
