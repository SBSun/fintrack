package com.sun.fintrack.trade.request;

import lombok.Getter;

/**
 * 거래 수정 요청
 */
@Getter
public class TradeModifyRequest {

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
