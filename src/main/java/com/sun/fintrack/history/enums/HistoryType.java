package com.sun.fintrack.history.enums;

import com.sun.fintrack.common.domain.enums.Codable;

import java.util.Arrays;

import lombok.Getter;

/**
 * 내역 타입
 */
@Getter
public enum HistoryType implements Codable {

  /**
   * 입금
   */
  DEPOSIT("D", "입금"),
  /**
   * 출금
   */
  WITHDRAW("W", "출금"),
  /**
   * 이체
   */
  TRANSFER("T", "이체");

  private final String code;
  private final String name;

  HistoryType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static boolean containCode(String code) {
    return Arrays.stream(HistoryType.values()).anyMatch(type -> type.code.equals(code));
  }

  public static HistoryType fromCode(String code) {
    return Codable.fromCode(HistoryType.class, code);
  }
}
