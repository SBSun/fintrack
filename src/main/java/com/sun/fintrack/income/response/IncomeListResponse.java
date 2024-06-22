package com.sun.fintrack.income.response;

import com.sun.fintrack.common.utils.DateTimeUtils;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 소득 내역 목록 조회 반환 형식
 */
@Getter
public class IncomeListResponse {

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
   * 카테고리 아이디
   */
  private Long categorySeq;
  /**
   * 카테고리명
   */
  private String categoryName;

  public IncomeListResponse(Long incomeSeq, String content, Long price, LocalDateTime incomeDt, Long categorySeq,
      String categoryName) {
    this.incomeSeq = incomeSeq;
    this.content = content;
    this.price = price;
    this.incomeDt = DateTimeUtils.formatLocalDateTime(incomeDt);
    this.categorySeq = categorySeq;
    this.categoryName = categoryName;
  }
}
