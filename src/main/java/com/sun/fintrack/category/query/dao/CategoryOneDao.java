package com.sun.fintrack.category.query.dao;

import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import static sun.Tables.CATEGORY;

/**
 * 카테고리 단일 조회 DAO
 */
@RequiredArgsConstructor
@Repository
public class CategoryOneDao {

  private final DSLContext dsl;

  /**
   * 특정 타입 카테고리의 마지막 순서 조회
   *
   * @param type 카테고리 타입
   * @return 요청 결과
   */
  public int selectTopOrder(TradeType type) {
    return Optional.ofNullable(dsl.select(CATEGORY.CTG_ORD)
                                  .from(CATEGORY)
                                  .where(CATEGORY.MB_SEQ.eq(MemberUtils.getMemberSeq()))
                                  .and(CATEGORY.CTG_TYP.eq(type.getCode()))
                                  .orderBy(CATEGORY.CTG_ORD.desc())
                                  .limit(1)
                                  .fetchOneInto(Integer.class)).orElse(0);
  }
}
