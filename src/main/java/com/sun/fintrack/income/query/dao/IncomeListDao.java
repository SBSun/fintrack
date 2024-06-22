package com.sun.fintrack.income.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.income.request.IncomeStatsRequest;
import com.sun.fintrack.income.response.IncomeListResponse;
import com.sun.fintrack.income.response.IncomeStatsResponse;
import com.sun.fintrack.payment.domain.enums.PeriodType;

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
import static sun.Tables.INCOME;

/**
 * 소득 목록 조회 DAO
 */
@RequiredArgsConstructor
@Repository
public class IncomeListDao {

  private final DSLContext dsl;

  /**
   * 소득 내역 카테고리별 통계 조회
   *
   * @param param 날짜
   */
  public IncomeStatsResponse select(IncomeStatsRequest param) {
    Result<Record3<Long, String, Long>> result =
        dsl.select(CATEGORY.CTG_SEQ, CATEGORY.CTG_NM, DSL.sum(INCOME.IC_PRC).cast(Long.class).as("SUM"))
           .from(INCOME)
           .join(CATEGORY)
           .on(CATEGORY.CTG_SEQ.eq(INCOME.CTG_SEQ))
           .where(getConditionList(param))
           .groupBy(INCOME.CTG_SEQ)
           .orderBy(DSL.field("SUM").desc())
           .fetch();

    Long totalPrice = result.stream().mapToLong(Record3::component3).sum();

    List<IncomeStatsResponse.Stats> list = result.stream().map(r -> {
      double rate = Math.round((r.component3() / (double) totalPrice * 100));
      return new IncomeStatsResponse.Stats(r.component1(), r.component2(), r.component3(), rate);
    }).toList();

    return new IncomeStatsResponse(totalPrice, list);
  }

  /**
   * 일일 소득 내역 목록 조회
   *
   * @param date 날짜
   */
  public List<IncomeListResponse> selectDailyList(String date) {
    return dsl.select(INCOME.IC_SEQ, INCOME.IC_CTT, INCOME.IC_PRC, INCOME.IC_DT, CATEGORY.CTG_SEQ, CATEGORY.CTG_NM)
              .from(INCOME)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(INCOME.CTG_SEQ))
              .where(INCOME.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(INCOME.IC_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date)))
              .orderBy(INCOME.IC_DT.desc())
              .fetchInto(IncomeListResponse.class);
  }

  /**
   * 월별 소득 내역 목록 조회
   *
   * @param year  년도
   * @param month 월
   */
  public List<IncomeListResponse> selectMonthlyList(Integer year, Integer month) {
    return dsl.select(INCOME.IC_SEQ, INCOME.IC_CTT, INCOME.IC_PRC, INCOME.IC_DT, CATEGORY.CTG_SEQ, CATEGORY.CTG_NM)
              .from(INCOME)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(INCOME.CTG_SEQ))
              .where(INCOME.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(DSL.year(INCOME.IC_DT).eq(year))
              .and(DSL.month(INCOME.IC_DT).eq(month))
              .orderBy(INCOME.IC_DT.desc())
              .fetchInto(IncomeListResponse.class);
  }

  /**
   * 소득 내용 검색 목록 조회
   *
   * @param keyword 검색 키워드
   */
  public List<IncomeListResponse> selectSearchList(String keyword) {
    return dsl.select(INCOME.IC_SEQ, INCOME.IC_CTT, INCOME.IC_PRC, INCOME.IC_DT, CATEGORY.CTG_SEQ, CATEGORY.CTG_NM)
              .from(INCOME)
              .join(CATEGORY)
              .on(CATEGORY.CTG_SEQ.eq(INCOME.CTG_SEQ))
              .where(getSearchConditionList(keyword))
              .orderBy(INCOME.IC_DT.desc())
              .fetchInto(IncomeListResponse.class);
  }

  /**
   * 소득 내역 카테고리별 통계 조회 조건
   */
  private List<Condition> getConditionList(IncomeStatsRequest param) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(INCOME.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    PeriodType periodType = PeriodType.fromCode(param.getPeriodType());

    if (periodType == PeriodType.DAILY) {
      LocalDate searchDate = DateTimeUtils.convertToDate(param.getSearchDate());
      conditionList.add(DSL.condition(INCOME.IC_DT.cast(LocalDate.class).eq(searchDate)));
    } else if (periodType == PeriodType.WEEKLY) {
      LocalDate searchDate = DateTimeUtils.convertToDate(param.getSearchDate());
      LocalDate startDate = searchDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
      LocalDate endDate = searchDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
      conditionList.add(DSL.condition(INCOME.IC_DT.cast(LocalDate.class).between(startDate, endDate)));
    } else if (periodType == PeriodType.MONTHLY) {
      conditionList.add(
          DSL.condition(DSL.year(INCOME.IC_DT).eq(param.getYear()).and(DSL.month(INCOME.IC_DT).eq(param.getMonth()))));
    } else if (periodType == PeriodType.YEARLY) {
      conditionList.add(DSL.condition(DSL.year(INCOME.IC_DT).eq(param.getYear())));
    } else {
      LocalDate startDate = DateTimeUtils.convertToDate(param.getStartDate());
      LocalDate endDate = DateTimeUtils.convertToDate(param.getEndDate());
      conditionList.add(DSL.condition(INCOME.IC_DT.cast(LocalDate.class).between(startDate, endDate)));
    }

    return conditionList;
  }

  /**
   * 소득 내용 검색 목록 조회 조건
   *
   * @param keyword 검색 키워드
   */
  private List<Condition> getSearchConditionList(String keyword) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(INCOME.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    if (StringUtils.isNotBlank(keyword)) {
      conditionList.add(INCOME.IC_CTT.like("%" + keyword + "%"));
    }
    return conditionList;
  }
}
