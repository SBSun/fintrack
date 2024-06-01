package com.sun.fintrack.payment.request;

import lombok.Getter;

/**
 * 결제 등록 정보
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
   * 카테고리 아이디
   */
  private Long categoryId;
}
