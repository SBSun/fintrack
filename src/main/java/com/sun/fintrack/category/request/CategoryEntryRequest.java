package com.sun.fintrack.category.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 등록 요청
 */
@Getter
@Setter
public class CategoryEntryRequest {

  /**
   * 카테고리명
   */
  private String name;
  /**
   * 카테고리 타입
   */
  private String type;
}
