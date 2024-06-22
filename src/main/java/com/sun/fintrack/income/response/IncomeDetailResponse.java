package com.sun.fintrack.income.response;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.income.domain.Income;

import lombok.Getter;

/**
 * 소득 단일 상세 조회 반환 형식
 */
@Getter
public class IncomeDetailResponse {

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
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 카테고리명
   */
  private String categoryName;

  public IncomeDetailResponse(Income income) {
    this.incomeSeq = income.getIncomeSeq();
    this.content = income.getContent();
    this.price = income.getPrice();
    this.incomeDt = DateTimeUtils.convertToString(income.getIncomeDt(), DateTimeUtils.DEFAULT_DATETIME);
    this.categorySeq = income.getCategory().getCategorySeq();
    this.categoryName = income.getCategory().getName();
  }
}
