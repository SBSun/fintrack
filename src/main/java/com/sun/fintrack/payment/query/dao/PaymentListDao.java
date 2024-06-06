package com.sun.fintrack.payment.query.dao;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.payment.response.PaymentDailyListResponse;
import com.sun.fintrack.payment.response.PaymentMonthlyListResponse;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
   */
  public List<PaymentDailyListResponse> getDailyList(String date) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.CRE_DT, PAYMENT_CATEGORY.PM_CTG_ID,
                  PAYMENT_CATEGORY.PM_CTG_NM)
              .from(PAYMENT)
              .join(PAYMENT_CATEGORY)
              .on(PAYMENT_CATEGORY.PM_CTG_ID.eq(PAYMENT.PM_CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(PAYMENT.CRE_DT.cast(LocalDate.class).eq(DateTimeUtils.convertToDate(date)))
              .orderBy(PAYMENT.CRE_DT.desc())
              .fetchInto(PaymentDailyListResponse.class);
  }

  /**
   * 월별 결제 내역 목록 조회
   */
  public List<PaymentMonthlyListResponse> getMonthlyList(Integer year, Integer month) {
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.CRE_DT, PAYMENT_CATEGORY.PM_CTG_ID,
                  PAYMENT_CATEGORY.PM_CTG_NM)
              .from(PAYMENT)
              .join(PAYMENT_CATEGORY)
              .on(PAYMENT_CATEGORY.PM_CTG_ID.eq(PAYMENT.PM_CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(MemberUtils.getMemberSeq()))
              .and(DSL.year(PAYMENT.CRE_DT).eq(year))
              .and(DSL.month(PAYMENT.CRE_DT).eq(month))
              .orderBy(PAYMENT.CRE_DT.desc())
              .fetchInto(PaymentMonthlyListResponse.class);
  }
}
