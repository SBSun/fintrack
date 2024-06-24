package com.sun.fintrack.transfer.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 이체 등록 요청
 */
@Getter
@Setter
public class TransferEntryRequest {

  /**
   * 이체 내용
   */
  private String content;
  /**
   * 이체 금액
   */
  private Long price;
  /**
   * 이체일시
   */
  private String transferDt;
  /**
   * 입금 자산 일련번호
   */
  private Long depositAssetSeq;
  /**
   * 출금 자산 일련번호
   */
  private Long withdrawAssetSeq;
}
