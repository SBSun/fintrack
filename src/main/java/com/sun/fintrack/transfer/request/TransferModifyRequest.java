package com.sun.fintrack.transfer.request;

import lombok.Getter;

/**
 * 거래 수정 요청
 */
@Getter
public class TransferModifyRequest {

  /**
   * 이체 일련번호
   */
  private Long transferSeq;
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
