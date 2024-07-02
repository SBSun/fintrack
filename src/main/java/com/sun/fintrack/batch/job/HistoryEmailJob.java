package com.sun.fintrack.batch.job;

import com.sun.fintrack.common.domain.enums.Valid;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.JacksonUtils;
import com.sun.fintrack.common.utils.MailUtils;
import com.sun.fintrack.history.query.service.HistoryOneService;
import com.sun.fintrack.history.response.HistoryResponse;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.repository.MemberRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 일일 내역 이메일 전송 작업
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class HistoryEmailJob {

  private final MemberRepository memberRepository;
  private final HistoryOneService historyOneService;
  private final MailUtils mailUtils;

  /**
   * 일일 내역 이메일 전송 실행
   */
  @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Seoul")
  public void execute() {
    List<Member> memberList =
        memberRepository.findAll().stream().filter(member -> member.getValid().equals(Valid.TRUE)).toList();

    String date = DateTimeUtils.convertToString(LocalDate.now().minusDays(1), DateTimeUtils.DEFAULT_DATE);

    memberList.forEach(member -> {
      HistoryResponse response = historyOneService.getOne(null, date, member.getMemberSeq());
      mailUtils.sendEmail(member.getEmail(), "일일(" + date + ") 거래 내역", JacksonUtils.toJson(response));
    });
  }
}