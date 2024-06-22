package com.sun.fintrack.asset.query.service;

import com.sun.fintrack.asset.query.dao.AssetListDao;
import com.sun.fintrack.asset.response.AssetListResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 자산 목록 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class AssetListService {

  private final AssetListDao assetListDao;

  /**
   * 자산 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<AssetListResponse> getList() {
    return assetListDao.select();
  }
}
