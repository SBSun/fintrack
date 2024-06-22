package com.sun.fintrack.category.response;

import lombok.Getter;

/**
 * 카테고리 목록 조회 반환 형식
 */
@Getter
public class CategoryListResponse {

  /**
   * 카테고리 아이디
   */
  private Long categoryId;
  /**
   * 카테고리명
   */
  private String name;

  public CategoryListResponse(Long categoryId, String name) {
    this.categoryId = categoryId;
    this.name = name;
  }
}
