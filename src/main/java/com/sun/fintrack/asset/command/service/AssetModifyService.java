package com.sun.fintrack.asset.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.asset.request.AssetModifyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 자산 수정 서비스
 */
@RequiredArgsConstructor
@Service
public class AssetModifyService {

  private final AssetOneService assetOneService;

  /**
   * 자산 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(AssetModifyRequest param) {
    Asset asset = assetOneService.getOne(param.getAssetSeq());

    asset.modify(param.getName(), param.getAmount());
  }
}