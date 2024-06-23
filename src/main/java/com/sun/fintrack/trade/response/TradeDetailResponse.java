package com.sun.fintrack.trade.response;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.trade.domain.Trade;

import lombok.Getter;

/**
 * 거래 단일 상세 조회 반환 형식
 */
@Getter
public class TradeDetailResponse {

  /**
   * 거래 일련번호
   */
  private Long tradeSeq;
  /**
   * 거래 내용
   */
  private String content;
  /**
   * 거래 금액
   */
  private Long price;
  /**
   * 거래일시
   */
  private String tradeDt;
  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 카테고리명
   */
  private String categoryName;

  public TradeDetailResponse(Trade trade) {
    this.tradeSeq = trade.getTradeSeq();
    this.content = trade.getContent();
    this.price = trade.getPrice();
    this.tradeDt = DateTimeUtils.convertToString(trade.getCreatedDt(), DateTimeUtils.DEFAULT_DATETIME);
    this.categorySeq = trade.getCategory().getCategorySeq();
    this.categoryName = trade.getCategory().getName();
  }
}
