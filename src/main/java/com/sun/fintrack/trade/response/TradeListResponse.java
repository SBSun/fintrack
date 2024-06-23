package com.sun.fintrack.trade.response;

import com.sun.fintrack.common.utils.DateTimeUtils;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 거래 내역 목록 조회 반환 형식
 */
@Getter
public class TradeListResponse {

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
   * 내역 이미지
   */
  private String image;
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

  public TradeListResponse(Long tradeSeq, String content, Long price, String image, LocalDateTime tradeDt,
      Long categorySeq, String categoryName) {
    this.tradeSeq = tradeSeq;
    this.content = content;
    this.price = price;
    this.image = image;
    this.tradeDt = DateTimeUtils.formatLocalDateTime(tradeDt);
    this.categorySeq = categorySeq;
    this.categoryName = categoryName;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
