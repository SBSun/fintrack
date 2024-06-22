package com.sun.fintrack.asset.response;

import lombok.Getter;

/**
 * 자산 목록 조회 반환 형식
 */
@Getter
public class AssetListResponse {

  /**
   * 자산 일련번호
   */
  private Long assetSeq;
  /**
   * 자산명
   */
  private String name;
  /**
   * 자산 금액
   */
  private Long amount;

  public AssetListResponse(Long assetSeq, String name, Long amount) {
    this.assetSeq = assetSeq;
    this.name = name;
    this.amount = amount;
  }
}
