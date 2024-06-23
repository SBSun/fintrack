package com.sun.fintrack.trade.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 월별 거래 내역 목록 조회 요청
 */
@Getter
@Setter
public class TradeMonthlyRequest {

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
