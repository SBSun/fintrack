package com.sun.fintrack.category.domain;

import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.common.domain.BaseTimeEntity;
import com.sun.fintrack.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "CATEGORY")
public class Category extends BaseTimeEntity {

  /**
   * 카테고리 ID
   */
  @Id
  @Column(name = "CTG_ID", nullable = false)
  private Long categoryId;
  /**
   * 카테고리명
   */
  @Column(name = "CTG_NM", nullable = false)
  private String name;
  /**
   * 카테고리 순서
   */
  @Column(name = "CTG_ORD", nullable = false)
  private Integer order;
  /**
   * 카테고리 타입
   */
  @Convert(converter = CategoryType.TypeCodeConverter.class)
  @Column(name = "CTG_TYP", nullable = false)
  private CategoryType type;
  /**
   * 회원
   */
  @ManyToOne
  @JoinColumn(name = "MB_SEQ", nullable = false)
  private Member member;

  public Category(String name, Integer order, CategoryType type, Member member) {
    this.name = name;
    this.order = order;
    this.type = type;
    this.member = member;
  }

  /**
   * 카테고리 정보 수정
   *
   * @param name  카테고리명
   * @param order 순서
   */
  public void modify(String name, Integer order) {
    this.name = name;
    this.order = order;
  }
}
