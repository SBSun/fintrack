package com.sun.fintrack.controller;

import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.history.query.service.HistoryOneService;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;
import com.sun.fintrack.validation.HistoryValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 내역 api
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/histories")
@RestController
public class HistoryController {

  private final HistoryOneService historyOneService;

  /**
   * 일일 내역 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/daily")
  public ResponseEntity<?> doGetDaily(@RequestParam(required = false) String type,
      @RequestParam(required = false) String date) {
    HistoryValidator.validateDaily(type, date);

    return ResponseEntity.ok(new DataResponse(historyOneService.getOne(type, date, MemberUtils.getMemberSeq())));
  }

  /**
   * 월별 내역 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/monthly")
  public ResponseEntity<?> doGetMonthly(@ModelAttribute HistoryMonthlyRequest param) {
    HistoryValidator.validate(param);

    return ResponseEntity.ok(new DataResponse(historyOneService.getOne(param)));
  }

  /**
   * 내역 내용 검색 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/search")
  public ResponseEntity<?> doGetSearch(@RequestParam(required = false) String keyword) {
    return ResponseEntity.ok(new DataResponse(historyOneService.getOne(keyword)));
  }
}
