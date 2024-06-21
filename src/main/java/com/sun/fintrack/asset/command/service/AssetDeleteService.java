package com.sun.fintrack.asset.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.repository.AssetRepository;
import com.sun.fintrack.asset.query.service.AssetOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 자산 정보 삭제 서비스
 */
@RequiredArgsConstructor
@Service
public class AssetDeleteService {

  private final AssetRepository assetRepository;
  private final AssetOneService assetOneService;

  /**
   * v 정보 삭제
   *
   * @param assetSeq 자산 일련번호
   */
  @Transactional
  public void delete(Long assetSeq) {
    Asset asset = assetOneService.getOne(assetSeq);
    assetRepository.delete(asset);
  }
}
