package com.sun.fintrack.history.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.history.enums.HistoryType;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;
import com.sun.fintrack.history.response.HistoryResponse;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import sun.tables.Asset;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import static sun.Tables.ASSET;
import static sun.Tables.CATEGORY;
import static sun.Tables.TRADE;
import static sun.Tables.TRANSFER;

/**
 * 내역 목록 조회 DAO
 */
@RequiredArgsConstructor
@Repository
public class HistoryListDao {

  private final DSLContext dsl;

  /**
   * 일일 거래 내역 목록 조회
   *
   * @param date 날짜
   */
  public List<HistoryResponse.History> selectDailyList(String type, String date, Long memberSeq) {
    if (StringUtils.isNotBlank(type)) {
      if (StringUtils.equals(type, HistoryType.TRANSFER.getCode())) {
        return selectTransferList(getDailyTransferConditionList(date, memberSeq));
      } else {
        return selectTradeList(getDailyTradeConditionList(type, date, memberSeq));
      }
    } else {
      return select(getDailyTradeConditionList(type, date, memberSeq), getDailyTransferConditionList(date, memberSeq));
    }
  }

  /**
   * 월별 거래 내역 목록 조회
   *
   * @param param 요청 파라미터
   */
  public List<HistoryResponse.History> selectMonthlyList(HistoryMonthlyRequest param) {
    if (StringUtils.isNotBlank(param.getType())) {
      if (StringUtils.equals(param.getType(), HistoryType.TRANSFER.getCode())) {
        return selectTransferList(getMonthlyTransferConditionList(param));
      } else {
        return selectTradeList(getMonthlyTradeConditionList(param));
      }
    } else {
      return select(getMonthlyTradeConditionList(param), getMonthlyTransferConditionList(param));
    }
  }

  /**
   * 내역 내용 검색 목록 조회
   *
   * @param keyword 검색 키워드
   */
  public List<HistoryResponse.History> selectSearchList(String keyword) {
    Long memberSeq = MemberUtils.getMemberSeq();
    Asset depositAsset = ASSET.as("depositAsset");
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_DT, TRADE.TRD_TYP, ASSET.AS_NM,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(ASSET)
              .on(ASSET.AS_SEQ.eq(TRADE.AS_SEQ))
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(TRADE.MB_SEQ.eq(memberSeq))
              .and(TRADE.TRD_CTT.like("%" + keyword + "%"))
              .unionAll(dsl.select(TRANSFER.TRF_SEQ, TRANSFER.TRF_CTT, TRANSFER.TRF_PRC, TRANSFER.TRF_DT,
                               DSL.val(HistoryType.TRANSFER.getCode()),
                               DSL.concat(ASSET.AS_NM).concat(" -> ").concat(depositAsset.AS_NM),
                               DSL.val(HistoryType.TRANSFER.getName()))
                           .from(TRANSFER)
                           .join(ASSET)
                           .on(ASSET.AS_SEQ.eq(TRANSFER.WTHDR_AS_SEQ))
                           .join(depositAsset)
                           .on(depositAsset.AS_SEQ.eq(TRANSFER.DPST_AS_SEQ))
                           .where(TRANSFER.MB_SEQ.eq(memberSeq))
                           .and(TRANSFER.TRF_CTT.like("%" + keyword + "%")))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }

  /**
   * 일일 거래 내역 목록 조회 조건
   */
  private List<Condition> getDailyTradeConditionList(String type, String date, Long memberSeq) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRADE.MB_SEQ.eq(memberSeq)));
    // 거래 타입
    if (StringUtils.isNotBlank(type)) {
      conditionList.add(DSL.condition(TRADE.TRD_TYP.eq(type)));
    }
    // 날짜
    conditionList.add(DSL.condition(TRADE.TRD_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date))));

    return conditionList;
  }

  /**
   * 일일 이체 내역 목록 조회 조건
   */
  private List<Condition> getDailyTransferConditionList(String date, Long memberSeq) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRANSFER.MB_SEQ.eq(memberSeq)));

    // 날짜
    conditionList.add(DSL.condition(TRANSFER.TRF_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date))));

    return conditionList;
  }

  /**
   * 월별 거래 내역 목록 조회 조건
   */
  private List<Condition> getMonthlyTradeConditionList(HistoryMonthlyRequest param) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRADE.MB_SEQ.eq(MemberUtils.getMemberSeq())));
    // 거래 타입
    if (StringUtils.isNotBlank(param.getType())) {
      conditionList.add(DSL.condition(TRADE.TRD_TYP.eq(param.getType())));
    }
    // 연도
    conditionList.add(DSL.condition(DSL.year(TRADE.TRD_DT).eq(param.getYear())));
    // 월
    conditionList.add(DSL.condition(DSL.month(TRADE.TRD_DT).eq(param.getMonth())));

    return conditionList;
  }

  /**
   * 월별 이체 내역 목록 조회 조건
   */
  private List<Condition> getMonthlyTransferConditionList(HistoryMonthlyRequest param) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRANSFER.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    // 연도
    conditionList.add(DSL.condition(DSL.year(TRANSFER.TRF_DT).eq(param.getYear())));
    // 월
    conditionList.add(DSL.condition(DSL.month(TRANSFER.TRF_DT).eq(param.getMonth())));

    return conditionList;
  }

  /**
   * 거래 & 이체 내역 목록 조회
   *
   * @param tradeConditionList    거래 내역 조회 조건 목록
   * @param transferConditionList 이체 내역 조회 조건 목록
   * @return 요청 결과
   */
  private List<HistoryResponse.History> select(List<Condition> tradeConditionList,
      List<Condition> transferConditionList) {
    Asset depositAsset = ASSET.as("depositAsset");
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_DT, TRADE.TRD_TYP, ASSET.AS_NM,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(ASSET)
              .on(ASSET.AS_SEQ.eq(TRADE.AS_SEQ))
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(tradeConditionList)
              .unionAll(dsl.select(TRANSFER.TRF_SEQ, TRANSFER.TRF_CTT, TRANSFER.TRF_PRC, TRANSFER.TRF_DT,
                               DSL.val(HistoryType.TRANSFER.getCode()),
                               DSL.concat(ASSET.AS_NM).concat(" -> ").concat(depositAsset.AS_NM),
                               DSL.val(HistoryType.TRANSFER.getName()))
                           .from(TRANSFER)
                           .join(ASSET)
                           .on(ASSET.AS_SEQ.eq(TRANSFER.WTHDR_AS_SEQ))
                           .join(depositAsset)
                           .on(depositAsset.AS_SEQ.eq(TRANSFER.DPST_AS_SEQ))
                           .where(transferConditionList))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }

  /**
   * 거래 내역 목록 조회
   *
   * @param conditionList 조건 목록
   * @return 요청 결과
   */
  private List<HistoryResponse.History> selectTradeList(List<Condition> conditionList) {
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_DT, TRADE.TRD_TYP, ASSET.AS_NM,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(ASSET)
              .on(ASSET.AS_SEQ.eq(TRADE.AS_SEQ))
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(conditionList)
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }

  /**
   * 이체 내역 목록 조회
   *
   * @param conditionList 조건 목록
   * @return 요청 결과
   */
  private List<HistoryResponse.History> selectTransferList(List<Condition> conditionList) {
    Asset depositAsset = ASSET.as("depositAsset");
    return dsl.select(TRANSFER.TRF_SEQ, TRANSFER.TRF_CTT, TRANSFER.TRF_PRC, TRANSFER.TRF_DT,
                  DSL.val(HistoryType.TRANSFER.getCode()), DSL.concat(ASSET.AS_NM).concat(" -> ").concat(depositAsset.AS_NM),
                  DSL.val(HistoryType.TRANSFER.getName()))
              .from(TRANSFER)
              .join(ASSET)
              .on(ASSET.AS_SEQ.eq(TRANSFER.WTHDR_AS_SEQ))
              .join(depositAsset)
              .on(depositAsset.AS_SEQ.eq(TRANSFER.DPST_AS_SEQ))
              .where(conditionList)
              .orderBy(TRANSFER.TRF_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }
}
