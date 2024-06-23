package com.sun.fintrack.trade.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.trade.domain.enums.PeriodType;
import com.sun.fintrack.trade.request.TradeMonthlyRequest;
import com.sun.fintrack.trade.request.TradeStatsRequest;
import com.sun.fintrack.trade.response.TradeListResponse;
import com.sun.fintrack.trade.response.TradeStatsResponse;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import static sun.Tables.CATEGORY;
import static sun.Tables.TRADE;

/**
 * 거래 목록 조회 DAO
 */
@RequiredArgsConstructor
@Repository
public class TradeListDao {

  private final DSLContext dsl;

  /**
   * 거래 내역 카테고리별 통계 조회
   *
   * @param param 요청 파라미터
   */
  public TradeStatsResponse select(TradeStatsRequest param) {
    Result<Record3<Long, String, Long>> result =
        dsl.select(CATEGORY.CTG_SEQ, CATEGORY.CTG_NM, DSL.sum(TRADE.TRD_PRC).cast(Long.class).as("SUM"))
           .from(TRADE)
           .join(CATEGORY)
           .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
           .where(getConditionList(param))
           .groupBy(TRADE.CTG_SEQ)
           .orderBy(DSL.field("SUM").desc())
           .fetch();

    Long totalPrice = result.stream().mapToLong(Record3::component3).sum();

    List<TradeStatsResponse.Stats> list = result.stream().map(r -> {
      double rate = Math.round((r.component3() / (double) totalPrice * 100));
      return new TradeStatsResponse.Stats(r.component1(), r.component2(), r.component3(), rate);
    }).toList();

    return new TradeStatsResponse(totalPrice, list);
  }

  /**
   * 일일 거래 내역 목록 조회
   *
   * @param date 날짜
   */
  public List<TradeListResponse> selectDailyList(String type, String date) {
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_IMG_PATH, TRADE.TRD_DT, CATEGORY.CTG_SEQ,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(TRADE.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(TRADE.TRD_TYP.eq(type))
              .and(TRADE.TRD_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date)))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(TradeListResponse.class);
  }

  /**
   * 월별 거래 내역 목록 조회
   *
   * @param param 요청 파라미터
   */
  public List<TradeListResponse> selectMonthlyList(TradeMonthlyRequest param) {
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_IMG_PATH, TRADE.TRD_DT, CATEGORY.CTG_SEQ,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(getConditionList(param))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(TradeListResponse.class);
  }

  /**
   * 거래 내용 검색 목록 조회
   *
   * @param keyword 검색 키워드
   */
  public List<TradeListResponse> selectSearchList(String keyword) {
    return dsl.select(TRADE.TRD_SEQ, TRADE.TRD_CTT, TRADE.TRD_PRC, TRADE.TRD_IMG_PATH, TRADE.TRD_DT, CATEGORY.CTG_SEQ,
                  CATEGORY.CTG_NM)
              .from(TRADE)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(TRADE.CTG_SEQ))
              .where(getSearchConditionList(keyword))
              .orderBy(TRADE.TRD_DT.desc())
              .fetchInto(TradeListResponse.class);
  }

  /**
   * 월별 거래 내역 목록 조회 조건
   */
  private List<Condition> getConditionList(TradeMonthlyRequest param) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(TRADE.MB_SEQ.eq(MemberUtils.getMemberSeq())));
    // 거래 타입
    conditionList.add(DSL.condition(TRADE.TRD_TYP.eq(param.getType())));
    // 연도
    conditionList.add(DSL.condition(DSL.year(TRADE.TRD_DT).eq(param.getYear())));
    // 월
    conditionList.add(DSL.condition(DSL.month(TRADE.TRD_DT).eq(param.getMonth())));

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
