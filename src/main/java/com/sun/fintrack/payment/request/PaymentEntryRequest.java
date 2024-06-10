package com.sun.fintrack.payment.request;

import lombok.Getter;

/**
 * 결제 등록 요청
 */
@Getter
public class PaymentEntryRequest {

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
}
