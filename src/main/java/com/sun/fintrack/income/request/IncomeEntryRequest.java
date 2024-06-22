package com.sun.fintrack.income.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 소득 등록 요청
 */
@Getter
@Setter
public class IncomeEntryRequest {

  /**
   * 소득 내용
   */
  private String content;
  /**
   * 소득 금액
   */
  private Long price;
  /**
   * 소득일시
   */
  private String incomeDt;
  /**
   * 자산 일련번호
   */
  private Long assetSeq;
  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
}
