package com.sun.fintrack.transfer.domain;

import com.sun.fintrack.asset.domain.Asset;
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
 * 이체 내역 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "TRANSFER")
public class Transfer extends BaseTimeEntity {

  /**
   * 이체 내역 일련번호
   */
  @Id
  @Column(name = "TRF_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transferSeq;
  /**
   * 이체 내용
   */
  @Column(name = "TRF_CTT", nullable = false)
  private String content;
  /**
   * 이체 금액
   */
  @Column(name = "TRF_PRC", nullable = false)
  private Long price;
  /**
   * 이체일시
   */
  @Column(name = "TRF_DT", nullable = false)
  private LocalDateTime transferDt;
  /**
   * 회원
   */
  @ManyToOne
  @JoinColumn(name = "MB_SEQ", nullable = false)
  private Member member;
  /**
   * 입금 자산
   */
  @ManyToOne
  @JoinColumn(name = "DPST_AS_SEQ", nullable = false)
  private Asset depositAsset;
  /**
   * 출금 자산
   */
  @ManyToOne
  @JoinColumn(name = "WTHDR_AS_SEQ", nullable = false)
  private Asset withdrawAsset;

  public Transfer(String content, Long price, LocalDateTime transferDt, Member member, Asset depositAsset,
      Asset withdrawAsset) {
    this.content = content;
    this.price = price;
    this.transferDt = transferDt;
    this.member = member;
    this.depositAsset = depositAsset;
    this.withdrawAsset = withdrawAsset;
  }

  /**
   * 이체 내역 수정
   *
   * @param content       이체 내용
   * @param price         이체 금액
   * @param transferDt    이체일시
   * @param depositAsset  입금 자산
   * @param withdrawAsset 출금 자산
   */
  public void modify(String content, Long price, LocalDateTime transferDt, Asset depositAsset, Asset withdrawAsset) {
    this.content = content;
    this.price = price;
    this.transferDt = transferDt;
    this.depositAsset = depositAsset;
    this.withdrawAsset = withdrawAsset;
  }
}
