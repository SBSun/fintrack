package com.sun.fintrack.common.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 이메일 전송 유틸
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtils {

  private final JavaMailSender emailSender;

  /**
   * 이메일 전송
   *
   * @param toEmail 수신 이메일
   * @param title   제목
   * @param text    내용
   */
  @Async
  public void sendEmail(String toEmail, String title, String text) {
    SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
    try {
      emailSender.send(emailForm);
    } catch (RuntimeException e) {
      log.debug("MailService.sendEmail exception occur toEmail: {}, " + "title: {}, text: {}", toEmail, title, text);
      throw new IllegalArgumentException("이메일 전송을 실패했습니다.");
    }
  }

  /**
   * 발신할 이메일 데이터 세팅
   *
   * @param toEmail 수신 이메일
   * @param title   제목
   * @param text    내용
   * @return SimpleMailMessage
   */
  private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(title);
    message.setText(text);

    return message;
  }
}
