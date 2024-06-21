package com.sun.fintrack.asset.query.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.repository.AssetRepository;
import com.sun.fintrack.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 자산 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class AssetOneService {

  private final AssetRepository assetRepository;

  /**
   * 자산 엔티티 단일 조회
   *
   * @param assetSeq 자산 일련번호
   * @return 자산 엔티티
   */
  @Transactional(readOnly = true)
  public Asset getOne(Long assetSeq) {
    return assetRepository.findById(assetSeq).orElseThrow(() -> new ValidationException("asset.not_found"));
  }
}
