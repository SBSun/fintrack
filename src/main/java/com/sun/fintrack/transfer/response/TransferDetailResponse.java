package com.sun.fintrack.transfer.response;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.transfer.domain.Transfer;

import lombok.Getter;

/**
 * 이체 단일 상세 조회 반환 형식
 */
@Getter
public class TransferDetailResponse {

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
   * 입금 자산명
   */
  private String depositAssetName;
  /**
   * 출금 자산 일련번호
   */
  private Long withdrawAssetSeq;
  /**
   * 출금 자산명
   */
  private String withdrawAssetName;

  public TransferDetailResponse(Transfer transfer) {
    this.transferSeq = transfer.getTransferSeq();
    this.content = transfer.getContent();
    this.price = transfer.getPrice();
    this.transferDt = DateTimeUtils.convertToString(transfer.getTransferDt(), DateTimeUtils.DEFAULT_DATETIME);
    Asset depositAsset = transfer.getDepositAsset();
    this.depositAssetSeq = depositAsset.getAssetSeq();
    this.depositAssetName = depositAsset.getName();
    Asset withdrawAsset = transfer.getWithdrawAsset();
    this.withdrawAssetSeq = withdrawAsset.getAssetSeq();
    this.withdrawAssetName = withdrawAsset.getName();
  }
}
