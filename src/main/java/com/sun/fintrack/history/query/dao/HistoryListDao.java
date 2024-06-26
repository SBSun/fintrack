package com.sun.fintrack.history.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.history.enums.HistoryType;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;
import com.sun.fintrack.history.response.HistoryResponse;
import com.sun.fintrack.trade.domain.enums.PeriodType;
import com.sun.fintrack.trade.request.TradeStatsRequest;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import sun.tables.Asset;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
  public List<HistoryResponse.History> selectDailyList(String type, String date) {
    Asset depositAsset = ASSET.as("depositAsset");
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_DT, TRADE.TRD_TYP, ASSET.AS_NM,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(ASSET)
              .on(ASSET.AS_SEQ.eq(TRADE.AS_SEQ))
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(getConditionList(type, date))
              .unionAll(dsl.select(TRANSFER.TRF_SEQ, TRANSFER.TRF_CTT, TRANSFER.TRF_PRC, TRANSFER.TRF_DT,
                               DSL.val(HistoryType.TRANSFER.getCode()),
                               DSL.concat(ASSET.AS_NM).concat(" -> ").concat(depositAsset.AS_NM),
                               DSL.val(HistoryType.TRANSFER.getName()))
                           .from(TRANSFER)
                           .join(ASSET)
                           .on(ASSET.AS_SEQ.eq(TRANSFER.WTHDR_AS_SEQ))
                           .join(depositAsset)
                           .on(depositAsset.AS_SEQ.eq(TRANSFER.DPST_AS_SEQ))
                           .where(TRANSFER.MB_SEQ.eq(MemberUtils.getMemberSeq()))
                           .and(TRANSFER.TRF_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date))))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }

  /**
   * 월별 거래 내역 목록 조회
   *
   * @param param 요청 파라미터
   */
  public List<HistoryResponse.History> selectMonthlyList(HistoryMonthlyRequest param) {
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_DT, TRADE.TRD_TYP, CATEGORY.CTG_NM)
              .from(TRADE)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(getConditionList(param))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }

  /**
   * 거래 내용 검색 목록 조회
   *
   * @param keyword 검색 키워드
   */
  public List<HistoryResponse.History> selectSearchList(String keyword) {
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_IMG_PATH, TRADE.TRD_DT, TRADE.TRD_TYP,
                  CATEGORY.CTG_SEQ, CATEGORY.CTG_NM)
              .from(TRADE)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(getSearchConditionList(keyword))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(HistoryResponse.History.class);
  }

  /**
   * 월별 거래 내역 목록 조회 조건
   */
  private List<Condition> getConditionList(HistoryMonthlyRequest param) {
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
   * 일일 거래 내역 목록 조회 조건
   */
  private List<Condition> getConditionList(String type, String date) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRADE.MB_SEQ.eq(MemberUtils.getMemberSeq())));
    // 거래 타입
    if (StringUtils.isNotBlank(type)) {
      conditionList.add(DSL.condition(TRADE.TRD_TYP.eq(type)));
    }
    // 날짜
    conditionList.add(DSL.condition(TRADE.TRD_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date))));

    return conditionList;
  }

  /**
   * 거래 내역 카테고리별 통계 조회 조건
   */
  private List<Condition> getConditionList(TradeStatsRequest param) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRADE.MB_SEQ.eq(MemberUtils.getMemberSeq())));
    // 거래 타입
    conditionList.add(DSL.condition(TRADE.TRD_TYP.eq(param.getTradeType())));

    PeriodType periodType = PeriodType.fromCode(param.getPeriodType());

    if (periodType == PeriodType.DAILY) {
      LocalDate searchDate = DateTimeUtils.convertToDate(param.getSearchDate());
      conditionList.add(DSL.condition(TRADE.TRD_DT.cast(LocalDate.class).eq(searchDate)));
    } else if (periodType == PeriodType.WEEKLY) {
      LocalDate searchDate = DateTimeUtils.convertToDate(param.getSearchDate());
      LocalDate startDate = searchDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
      LocalDate endDate = searchDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
      conditionList.add(DSL.condition(TRADE.TRD_DT.cast(LocalDate.class).between(startDate, endDate)));
    } else if (periodType == PeriodType.MONTHLY) {
      conditionList.add(
          DSL.condition(DSL.year(TRADE.TRD_DT).eq(param.getYear()).and(DSL.month(TRADE.TRD_DT).eq(param.getMonth()))));
    } else if (periodType == PeriodType.YEARLY) {
      conditionList.add(DSL.condition(DSL.year(TRADE.TRD_DT).eq(param.getYear())));
    } else {
      LocalDate startDate = DateTimeUtils.convertToDate(param.getStartDate());
      LocalDate endDate = DateTimeUtils.convertToDate(param.getEndDate());
      conditionList.add(DSL.condition(TRADE.TRD_DT.cast(LocalDate.class).between(startDate, endDate)));
    }

    return conditionList;
  }

  /**
   * 거래 내용 검색 목록 조회 조건
   *
   * @param keyword 검색 키워드
   */
  private List<Condition> getSearchConditionList(String keyword) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRADE.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    if (StringUtils.isNotBlank(keyword)) {
      conditionList.add(TRADE.TRD_CTT.like("%" + keyword + "%"));
    }
    return conditionList;
  }
}
