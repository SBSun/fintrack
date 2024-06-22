package com.sun.fintrack.asset.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.repository.AssetRepository;
import com.sun.fintrack.asset.request.AssetEntryRequest;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 자산 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class AssetEntryService {

  private final AssetRepository assetRepository;

  private final MemberOneService memberOneService;

  /**
   * 자산 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(AssetEntryRequest param) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());

    assetRepository.save(new Asset(param.getName(), param.getAmount(), member));
  }
}
