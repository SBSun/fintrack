package com.sun.fintrack.history.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 월별 내역 목록 조회 요청
 */
@Getter
@Setter
public class HistoryMonthlyRequest {

  /**
   * 거래 타입
   */
  private String type;
  /**
   * 연도
   */
  private Integer year;
  /**
   * 월
   */
  private Integer month;
}
