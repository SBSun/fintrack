package com.sun.fintrack.trade.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 거래 등록 요청
 */
@Getter
@Setter
public class TradeEntryRequest {

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
   * 거래 타입
   */
  private String type;
  /**
   * 자산 일련번호
   */
  private Long assetSeq;
  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
}
