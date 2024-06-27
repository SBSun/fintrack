package com.sun.fintrack.asset.query.dao;

import com.sun.fintrack.asset.response.AssetListResponse;
import com.sun.fintrack.common.utils.MemberUtils;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

import static sun.Tables.ASSET;

/**
 * 자산 목록 조회 DAO
 */
@RequiredArgsConstructor
@Repository
public class AssetListDao {

  private final DSLContext dsl;

  /**
   * 자산 목록 조회
   *
   * @return 요쳥 결과
   */
  public List<AssetListResponse> select() {
    return dsl.select(ASSET.AS_SEQ, ASSET.AS_NM, ASSET.AS_AMT)
              .from(ASSET)
              .where(ASSET.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .orderBy(ASSET.AS_SEQ.asc())
              .fetchInto(AssetListResponse.class);
  }
}
