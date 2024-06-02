package com.sun.fintrack.payment.response;

import com.sun.fintrack.common.utils.DateTimeUtils;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 일일 결제 목록 조회 반환 형식
 */
@Getter
public class PaymentDailyListResponse {

  /**
   * 결제 일련번호
   */
  private Long paymentSeq;
  /**
   * 결제 내용
   */
  private String content;
  /**
   * 결제 금액
   */
  private Long price;
  /**
   * 결제일시
   */
  private String paymentDt;
  /**
   * 카테고리 아이디
   */
  private Long categoryId;
  /**
   * 카테고리명
   */
  private String categoryName;

  public PaymentDailyListResponse(Long paymentSeq, String content, Long price, LocalDateTime paymentDt, Long categoryId,
      String categoryName) {
    this.paymentSeq = paymentSeq;
    this.content = content;
    this.price = price;
    this.paymentDt = DateTimeUtils.convertToString(paymentDt, DateTimeUtils.DEFAULT_DATETIME);
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }
}
