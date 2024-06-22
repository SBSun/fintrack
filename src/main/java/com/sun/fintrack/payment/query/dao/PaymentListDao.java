package com.sun.fintrack.payment.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.payment.domain.enums.PeriodType;
import com.sun.fintrack.payment.request.PaymentStatsRequest;
import com.sun.fintrack.payment.response.PaymentListResponse;
import com.sun.fintrack.payment.response.PaymentStatsResponse;

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
import static sun.Tables.PAYMENT;

@RequiredArgsConstructor
@Repository
public class PaymentListDao {

  private final DSLContext dsl;

  /**
   * 일일 결제 내역 목록 조회
   *
   * @param date 날짜
   */
  public List<PaymentListResponse> getDailyList(String date) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.PM_IMG_PATH, PAYMENT.PM_DT,
                  CATEGORY.CTG_ID, CATEGORY.CTG_NM)
              .from(PAYMENT)
              .join(CATEGORY)
              .on(CATEGORY.CTG_ID.eq(PAYMENT.CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(PAYMENT.PM_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date)))
              .orderBy(PAYMENT.PM_DT.desc())
              .fetchInto(PaymentListResponse.class);
  }

  /**
   * 결제 내역 카테고리별 통계 조회
   *
   * @param param 날짜
   */
  public PaymentStatsResponse getList(PaymentStatsRequest param) {
    Result<Record3<Long, String, Long>> result =
        dsl.select(CATEGORY.CTG_ID, CATEGORY.CTG_NM, DSL.sum(PAYMENT.PM_PRC).cast(Long.class).as("SUM"))
           .from(PAYMENT)
           .join(CATEGORY)
           .on(CATEGORY.CTG_ID.eq(PAYMENT.CTG_ID))
           .where(getConditionList(param))
           .groupBy(PAYMENT.CTG_ID)
           .orderBy(DSL.field("SUM").desc())
           .fetch();

    Long totalPrice = result.stream().mapToLong(Record3::component3).sum();

    List<PaymentStatsResponse.Stats> list = result.stream().map(r -> {
      double rate = Math.round((r.component3() / (double) totalPrice * 100));
      return new PaymentStatsResponse.Stats(r.component1(), r.component2(), r.component3(), rate);
    }).toList();

    return new PaymentStatsResponse(totalPrice, list);
  }

  /**
   * 월별 결제 내역 목록 조회
   *
   * @param year  년도
   * @param month 월
   */
  public List<PaymentListResponse> getMonthlyList(Integer year, Integer month) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.PM_IMG_PATH, PAYMENT.PM_DT,
                  CATEGORY.CTG_ID, CATEGORY.CTG_NM)
              .from(PAYMENT)
              .join(CATEGORY)
              .on(CATEGORY.CTG_ID.eq(PAYMENT.CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(DSL.year(PAYMENT.PM_DT).eq(year))
              .and(DSL.month(PAYMENT.PM_DT).eq(month))
              .orderBy(PAYMENT.PM_DT.desc())
              .fetchInto(PaymentListResponse.class);
  }

  /**
   * 결제 내용 검색 목록 조회
   *
   * @param keyword 검색 키워드
   */
  public List<PaymentListResponse> getSearchList(String keyword) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.PM_IMG_PATH, PAYMENT.PM_DT,
                  CATEGORY.CTG_ID, CATEGORY.CTG_NM)
              .from(PAYMENT)
              .join(CATEGORY)
              .on(CATEGORY.CTG_ID.eq(PAYMENT.CTG_ID))
              .where(getSearchConditionList(keyword))
              .orderBy(PAYMENT.PM_DT.desc())
              .fetchInto(PaymentListResponse.class);
  }

  /**
   * 결제 내역 카테고리별 통계 조회 조건
   */
  private List<Condition> getConditionList(PaymentStatsRequest param) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    PeriodType periodType = PeriodType.fromCode(param.getPeriodType());

    if (periodType == PeriodType.DAILY) {
      LocalDate searchDate = DateTimeUtils.convertToDate(param.getSearchDate());
      conditionList.add(DSL.condition(PAYMENT.PM_DT.cast(LocalDate.class).eq(searchDate)));
    } else if (periodType == PeriodType.WEEKLY) {
      LocalDate searchDate = DateTimeUtils.convertToDate(param.getSearchDate());
      LocalDate startDate = searchDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
      LocalDate endDate = searchDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
      conditionList.add(DSL.condition(PAYMENT.PM_DT.cast(LocalDate.class).between(startDate, endDate)));
    } else if (periodType == PeriodType.MONTHLY) {
      conditionList.add(DSL.condition(
          DSL.year(PAYMENT.PM_DT).eq(param.getYear()).and(DSL.month(PAYMENT.PM_DT).eq(param.getMonth()))));
    } else if (periodType == PeriodType.YEARLY) {
      conditionList.add(DSL.condition(DSL.year(PAYMENT.PM_DT).eq(param.getYear())));
    } else {
      LocalDate startDate = DateTimeUtils.convertToDate(param.getStartDate());
      LocalDate endDate = DateTimeUtils.convertToDate(param.getEndDate());
      conditionList.add(DSL.condition(PAYMENT.PM_DT.cast(LocalDate.class).between(startDate, endDate)));
    }

    return conditionList;
  }

  /**
   * 결제 내용 검색 목록 조회 조건
   *
   * @param keyword 검색 키워드
   */
  private List<Condition> getSearchConditionList(String keyword) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    if (StringUtils.isNotBlank(keyword)) {
      conditionList.add(PAYMENT.PM_CTT.like("%" + keyword + "%"));
    }
    return conditionList;
  }
}
