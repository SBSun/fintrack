package com.sun.fintrack.payment.query.dao;

import com.sun.fintrack.payment.response.PaymentDailyListResponse;

import org.jooq.DSLContext;
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

  public List<PaymentDailyListResponse> getList(Long memberSeq) {
    LocalDate curDate = LocalDate.now();
    return dsl.select(PAYMENT.PM_SEQ, PAYMENT.PM_CTT, PAYMENT.PM_PRC, PAYMENT.CRE_DT, PAYMENT_CATEGORY.PM_CTG_ID,
                  PAYMENT_CATEGORY.PM_CTG_NM)
              .from(PAYMENT)
              .join(PAYMENT_CATEGORY)
              .on(PAYMENT_CATEGORY.PM_CTG_ID.eq(PAYMENT.PM_CTG_ID))
              .where(PAYMENT.MB_SEQ.eq(memberSeq))
              .and(PAYMENT.CRE_DT.cast(LocalDate.class).eq(curDate))
              .orderBy(PAYMENT.CRE_DT.desc())
              .fetchInto(PaymentDailyListResponse.class);
  }
}
