package com.sun.fintrack.category.query.dao;

import com.sun.fintrack.category.response.CategoryListResponse;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

import static sun.Tables.CATEGORY;

/**
 * 카테고리 목록 조회 DAO
 */
@RequiredArgsConstructor
@Repository
public class CategoryListDao {

  private final DSLContext dsl;

  /**
   * 카테고리 목록 조회
   *
   * @param type 카테고리 타입
   * @return 요쳥 결과
   */
  public List<CategoryListResponse> select(TradeType type) {
    return dsl.select(CATEGORY.CTG_SEQ, CATEGORY.CTG_NM)
              .from(CATEGORY)
              .where(CATEGORY.CTG_TYP.eq(type.getCode()))
              .and(CATEGORY.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .orderBy(CATEGORY.CTG_ORD.asc())
              .fetchInto(CategoryListResponse.class);
  }
}
