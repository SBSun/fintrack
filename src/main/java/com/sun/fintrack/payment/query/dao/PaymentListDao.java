package com.sun.fintrack.payment.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.payment.response.PaymentListResponse;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import static sun.Tables.PAYMENT;
import static sun.Tables.PAYMENT_CATEGORY;

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
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.CRE_DT, PAYMENT_CATEGORY.PM_CTG_ID,
                  PAYMENT_CATEGORY.PM_CTG_NM)
              .from(PAYMENT)
              .join(PAYMENT_CATEGORY)
              .on(PAYMENT_CATEGORY.PM_CTG_ID.eq(PAYMENT.PM_CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(PAYMENT.CRE_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date)))
              .orderBy(PAYMENT.CRE_DT.desc())
              .fetchInto(PaymentListResponse.class);
  }

  /**
   * 월별 결제 내역 목록 조회
   *
   * @param year  년도
   * @param month 월
   */
  public List<PaymentListResponse> getMonthlyList(Integer year, Integer month) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.CRE_DT, PAYMENT_CATEGORY.PM_CTG_ID,
                  PAYMENT_CATEGORY.PM_CTG_NM)
              .from(PAYMENT)
              .join(PAYMENT_CATEGORY)
              .on(PAYMENT_CATEGORY.PM_CTG_ID.eq(PAYMENT.PM_CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(DSL.year(PAYMENT.CRE_DT).eq(year))
              .and(DSL.month(PAYMENT.CRE_DT).eq(month))
              .orderBy(PAYMENT.CRE_DT.desc())
              .fetchInto(PaymentListResponse.class);
  }

  /**
   * 결제 내용 검색 목록 조회
   *
   * @param keyword 검색 키워드
   */
  public List<PaymentListResponse> getSearchList(String keyword) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.CRE_DT, PAYMENT_CATEGORY.PM_CTG_ID,
                  PAYMENT_CATEGORY.PM_CTG_NM)
              .from(PAYMENT)
              .join(PAYMENT_CATEGORY)
              .on(PAYMENT_CATEGORY.PM_CTG_ID.eq(PAYMENT.PM_CTG_ID))
              .where(getSearchConditionList(keyword))
              .orderBy(PAYMENT.CRE_DT.desc())
              .fetchInto(PaymentListResponse.class);
  }

  /**
   * 결제 내용 검색 목록 조회 조건
   *
   * @param keyword 검색 키워드
   */
  private List<Condition> getSearchConditionList(String keyword) {
    List<Condition> conditionList = new ArrayList<>();

    conditionList.add(DSL.condition(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq())));

    if (!StringUtils.isBlank(keyword)) {
      conditionList.add(PAYMENT.PM_CTT.like("%" + keyword + "%"));
    }
    return conditionList;
  }
}
