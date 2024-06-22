package com.sun.fintrack.income.request;

import lombok.Getter;

/**
 * 소득 수정 요청
 */
@Getter
public class IncomeModifyRequest {

  /**
   * 소득 일련번호
   */
  private Long incomeSeq;
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
