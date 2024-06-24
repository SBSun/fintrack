package com.sun.fintrack.transfer.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;
import com.sun.fintrack.transfer.domain.Transfer;
import com.sun.fintrack.transfer.query.repository.TransferRepository;
import com.sun.fintrack.transfer.request.TransferEntryRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 이체 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class TransferEntryService {

  private final TransferRepository transferRepository;

  private final AssetOneService assetOneService;
  private final MemberOneService memberOneService;

  /**
   * 이체 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(TransferEntryRequest param) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());
    Asset depositAsset = assetOneService.getOne(param.getDepositAssetSeq());
    Asset withdrawAsset = assetOneService.getOne(param.getWithdrawAssetSeq());

    transferRepository.save(
        new Transfer(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getTransferDt()),
            member, depositAsset, withdrawAsset));
  }
}
