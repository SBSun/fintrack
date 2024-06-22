package com.sun.fintrack.category.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 수정 요청
 */
@Getter
@Setter
public class CategoryModifyRequest {

  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 카테고리명
   */
  private String name;
  /**
   * 카테고리 타입
   */
  private String type;
}
