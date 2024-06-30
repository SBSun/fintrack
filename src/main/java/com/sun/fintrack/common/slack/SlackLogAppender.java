package com.sun.fintrack.common.slack;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.sun.fintrack.common.utils.DateTimeUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import static com.slack.api.webhook.WebhookPayloads.payload;

/**
 * Slack에 에러 로그를 전송하는 Appender
 */
@Profile("dev")
@Slf4j
@Component
public class SlackLogAppender {

  private final Slack slackClient = Slack.getInstance();

  @Value("${slack.webhook.url}")
  private String webhookUrl;

  @Async
  public void sendErrorLog(Exception e, HttpServletRequest request) {
    try {
      slackClient.send(webhookUrl,
          payload(p -> p.text("서버 에러 발생").attachments(List.of(generateSlackAttachment(e, request)))));
    } catch (IOException slackError) {
      log.error("Slack 통신과의 예외 발생");
    }
  }

  private Attachment generateSlackAttachment(Exception e, HttpServletRequest request) {
    String requestTime = DateTimeUtils.convertToString(LocalDateTime.now(), DateTimeUtils.DEFAULT_DATETIME);
    String xffHeader = request.getHeader("X-FORWARDED-FOR");
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    String message = sw.toString().substring(0, 2000);
    return Attachment.builder()
                     .serviceName("에러 알리미")
                     .color("ff0000")
                     .title(requestTime + " 발생 에러 로그")
                     .fields(List.of(generateSlackField("에러 내용", message),
                         generateSlackField("요청 IP", xffHeader == null ? request.getRemoteAddr() : xffHeader),
                         generateSlackField("요청 URI", "[" + request.getMethod() + "] " + request.getRequestURI())))
                     .build();
  }

  private Field generateSlackField(String title, String value) {
    return Field.builder().title(title).value(value).valueShortEnough(false).build();
  }
}
