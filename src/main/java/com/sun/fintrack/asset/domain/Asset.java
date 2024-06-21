package com.sun.fintrack.asset.domain;

import com.sun.fintrack.common.domain.BaseTimeEntity;
import com.sun.fintrack.member.domain.Member;

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
 * 자산 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "ASSET")
public class Asset extends BaseTimeEntity {

  /**
   * 자산 일련번호
   */
  @Id
  @Column(name = "AS_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long assetSeq;
  /**
   * 자산명
   */
  @Column(name = "AS_NM", nullable = false)
  private String name;
  /**
   * 자산 금액
   */
  @Column(name = "AS_AMT", nullable = false)
  private Long amount;

  /**
   * 회원
   */
  @ManyToOne
  @JoinColumn(name = "MB_SEQ", nullable = false)
  private Member member;

  public Asset(String name, Long amount, Member member) {
    this.name = name;
    this.amount = amount;
    this.member = member;
  }

  /**
   * 자산 정보 수정
   *
   * @param name   자산명
   * @param amount 자산 금액
   */
  public void modify(String name, Long amount) {
    this.name = name;
    this.amount = amount;
  }
}