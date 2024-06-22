package com.sun.fintrack.income.domain;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.common.domain.BaseTimeEntity;
import com.sun.fintrack.member.domain.Member;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소득 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "INCOME")
public class Income extends BaseTimeEntity {

  /**
   * 소득 일련번호
   */
  @Id
  @Column(name = "IC_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long incomeSeq;
  /**
   * 소득 내용
   */
  @Column(name = "IC_CTT", nullable = false)
  private String content;
  /**
   * 소득 금액
   */
  @Column(name = "IC_PRC", nullable = false)
  private Long price;
  /**
   * 소득일시
   */
  @Column(name = "IC_DT", nullable = false)
  private LocalDateTime incomeDt;
  /**
   * 회원
   */
  @ManyToOne
  @JoinColumn(name = "MB_SEQ", nullable = false)
  private Member member;
  /**
   * 자산
   */
  @ManyToOne
  @JoinColumn(name = "AS_SEQ", nullable = false)
  private Asset asset;
  /**
   * 카테고리
   */
  @ManyToOne
  @JoinColumn(name = "CTG_SEQ", nullable = false)
  private Category category;

  public Income(String content, Long price, LocalDateTime incomeDt, Member member, Asset asset, Category category) {
    this.content = content;
    this.price = price;
    this.incomeDt = incomeDt;
    this.member = member;
    this.asset = asset;
    this.category = category;
  }

  /**
   * 소득 정보 수정
   *
   * @param content  소득 내용
   * @param price    소득 금액
   * @param incomeDt 소득일시
   * @param asset    자산
   * @param category 카테고리
   */
  public void modify(String content, Long price, LocalDateTime incomeDt, Asset asset, Category category) {
    this.content = content;
    this.price = price;
    this.incomeDt = incomeDt;
    this.asset = asset;
    this.category = category;
  }
}
