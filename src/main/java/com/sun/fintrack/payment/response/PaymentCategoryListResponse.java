package com.sun.fintrack.payment.response;

import lombok.Getter;

/**
 * 결제 카테고리 목록 조회 반환 형식
 */
@Getter
public class PaymentCategoryListResponse {

  /**
   * 카테고리 아이디
   */
  private Long categoryId;
  /**
   * 카테고리명
   */
  private String name;

  public PaymentCategoryListResponse(Long categoryId, String name) {
    this.categoryId = categoryId;
    this.name = name;
  }
}
