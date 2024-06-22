package com.sun.fintrack.category.response;

import lombok.Getter;

/**
 * 카테고리 목록 조회 반환 형식
 */
@Getter
public class CategoryListResponse {

  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 카테고리명
   */
  private String name;

  public CategoryListResponse(Long categorySeq, String name) {
    this.categorySeq = categorySeq;
    this.name = name;
  }
}
