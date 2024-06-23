package com.sun.fintrack.trade.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 거래 내역 카테고리별 통계 조회 반환 형식
 */
@Getter
public class TradeStatsResponse {

  /**
   * 총 금액
   */
  private Long totalPrice;
  /**
   * 카테고리별 통계 목록
   */
  private List<Stats> statsList;

  public TradeStatsResponse(Long totalPrice, List<Stats> statsList) {
    this.totalPrice = totalPrice;
    this.statsList = statsList;
  }

  @Getter
  @Setter
  public static class Stats {

    /**
     * 카테고리 일련번호
     */
    private Long categorySeq;
    /**
     * 카테고리명
     */
    private String categoryName;
    /**
     * 금액
     */
    private Long price;
    /**
     * 비율
     */
    private Double rate;

    public Stats(Long categorySeq, String categoryName, Long price, double rate) {
      this.categorySeq = categorySeq;
      this.categoryName = categoryName;
      this.price = price;
      this.rate = rate;
    }
  }
}
