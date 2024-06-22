package com.sun.fintrack.controller;

import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.response.ListResponse;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.income.command.service.IncomeDeleteService;
import com.sun.fintrack.income.command.service.IncomeEntryService;
import com.sun.fintrack.income.command.service.IncomeModifyService;
import com.sun.fintrack.income.query.service.IncomeListService;
import com.sun.fintrack.income.query.service.IncomeOneService;
import com.sun.fintrack.income.request.IncomeEntryRequest;
import com.sun.fintrack.income.request.IncomeModifyRequest;
import com.sun.fintrack.income.request.IncomeStatsRequest;
import com.sun.fintrack.validation.IncomeValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/incomes")
@RestController
public class IncomeController {

  private final IncomeEntryService incomeEntryService;
  private final IncomeModifyService incomeModifyService;
  private final IncomeDeleteService incomeDeleteService;

  private final IncomeOneService incomeOneService;
  private final IncomeListService incomeListService;

  /**
   * 소득 삭제
   *
   * @param incomeSeq 소득 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/{incomeSeq}")
  public ResponseEntity<?> doDelete(@PathVariable("incomeSeq") Long incomeSeq) {
    IncomeValidator.validateSeq(incomeSeq);

    incomeDeleteService.delete(incomeSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 소득 단일 상세 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/{incomeSeq}")
  public ResponseEntity<?> doGet(@PathVariable("incomeSeq") Long incomeSeq) {
    IncomeValidator.validateSeq(incomeSeq);

    return ResponseEntity.ok(new DataResponse(incomeOneService.getDetail(incomeSeq)));
  }

  /**
   * 일일 소득 내역 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/daily")
  public ResponseEntity<?> doGetDaily(@RequestParam(required = false) String date) {
    IncomeValidator.validateDaily(date);

    return ResponseEntity.ok(new ListResponse(incomeListService.getDailyList(date)));
  }

  /**
   * 월별 소득 내역 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/monthly")
  public ResponseEntity<?> doGetMonthly(@RequestParam(required = false) Integer year,
      @RequestParam(required = false) Integer month) {
    IncomeValidator.validateMonthly(year, month);

    return ResponseEntity.ok(new ListResponse(incomeListService.getMonthlyList(year, month)));
  }

  /**
   * 소득 내용 검색 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/search")
  public ResponseEntity<?> doGetSearch(@RequestParam(required = false) String keyword) {
    return ResponseEntity.ok(new ListResponse(incomeListService.getSearchList(keyword)));
  }

  /**
   * 소득 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/stats")
  public ResponseEntity<?> doGetStats(@ModelAttribute IncomeStatsRequest param) {
    IncomeValidator.validate(param);

    return ResponseEntity.ok(new DataResponse(incomeListService.getList(param)));
  }

  /**
   * 소득 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestBody IncomeEntryRequest param) {
    IncomeValidator.validate(param);

    incomeEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 소득 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody IncomeModifyRequest param) {
    IncomeValidator.validate(param);

    incomeModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
