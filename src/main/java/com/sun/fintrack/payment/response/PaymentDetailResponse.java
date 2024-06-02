package com.sun.fintrack.payment.response;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.payment.domain.Payment;

import lombok.Getter;

/**
 * 결제 단일 상세 조회 반환 형식
 */
@Getter
public class PaymentDetailResponse {

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

  public PaymentDetailResponse(Payment payment) {
    this.paymentSeq = payment.getPaymentSeq();
    this.content = payment.getContent();
    this.price = payment.getPrice();
    this.paymentDt = DateTimeUtils.convertToString(payment.getCreatedDt(), DateTimeUtils.DEFAULT_DATETIME);
    this.categoryId = payment.getCategory().getCategoryId();
    this.categoryName = payment.getCategory().getName();
  }
}
