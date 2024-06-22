package com.sun.fintrack.asset.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 자산 수정 요청
 */
@Getter
@Setter
public class AssetModifyRequest {

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
}
