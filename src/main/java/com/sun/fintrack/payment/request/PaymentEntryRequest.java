package com.sun.fintrack.payment.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 결제 등록 요청
 */
@Getter
@Setter
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
   * 자산 일련번호
   */
  private Long assetSeq;
  /**
   * 카테고리 아이디
   */
  private Long categoryId;
}
