package com.sun.fintrack.transfer.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.transfer.domain.Transfer;
import com.sun.fintrack.transfer.query.service.TransferOneService;
import com.sun.fintrack.transfer.request.TransferModifyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 이체 수정 서비스
 */
@RequiredArgsConstructor
@Service
public class TransferModifyService {

  private final TransferOneService transferOneService;
  private final AssetOneService assetOneService;

  /**
   * 이체 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(TransferModifyRequest param) {
    Transfer transfer = transferOneService.getOne(param.getTransferSeq());
    Asset depositAsset = assetOneService.getOne(param.getDepositAssetSeq());
    Asset withdrawAsset = assetOneService.getOne(param.getWithdrawAssetSeq());
    
    transfer.modify(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getTransferDt()),
        depositAsset, withdrawAsset);
  }
}