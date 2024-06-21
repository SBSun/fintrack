package com.sun.fintrack.asset.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 자산 등록 요청
 */
@Getter
@Setter
public class AssetEntryRequest {

  /**
   * 자산명
   */
  private String name;
  /**
   * 자산 금액
   */
  private Long amount;
}
