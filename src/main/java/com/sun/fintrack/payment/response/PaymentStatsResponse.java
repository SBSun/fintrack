package com.sun.fintrack.payment.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 결제 내역 카테고리별 통계 조회 반환 형식
 */
@Getter
public class PaymentStatsResponse {

  /**
   * 총 금액
   */
  private Long totalPrice;
  /**
   * 카테고리별 통계 목록
   */
  private List<Stats> statsList;

  public PaymentStatsResponse(Long totalPrice, List<Stats> statsList) {
    this.totalPrice = totalPrice;
    this.statsList = statsList;
  }

  @Getter
  @Setter
  public static class Stats {

    /**
     * 카테고리 ID
     */
    private Long categoryId;
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

    public Stats(Long categoryId, String categoryName, Long price, double rate) {
      this.categoryId = categoryId;
      this.categoryName = categoryName;
      this.price = price;
      this.rate = rate;
    }
  }
}
