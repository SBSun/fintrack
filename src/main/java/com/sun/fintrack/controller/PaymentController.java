package com.sun.fintrack.controller;

import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.response.ListResponse;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.payment.command.service.PaymentDeleteService;
import com.sun.fintrack.payment.command.service.PaymentEntryService;
import com.sun.fintrack.payment.command.service.PaymentModifyService;
import com.sun.fintrack.payment.query.service.PaymentListService;
import com.sun.fintrack.payment.query.service.PaymentOneService;
import com.sun.fintrack.payment.request.PaymentEntryRequest;
import com.sun.fintrack.payment.request.PaymentModifyRequest;
import com.sun.fintrack.payment.request.PaymentStatsRequest;
import com.sun.fintrack.validation.PaymentValidator;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/payments")
@RestController
public class PaymentController {

  private final PaymentEntryService paymentEntryService;
  private final PaymentModifyService paymentModifyService;
  private final PaymentDeleteService paymentDeleteService;

  private final PaymentOneService paymentOneService;
  private final PaymentListService paymentListService;

  /**
   * 결제 삭제
   *
   * @param paymentSeq 결제 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/{paymentSeq}")
  public ResponseEntity<?> doDelete(@PathVariable("paymentSeq") Long paymentSeq) {
    PaymentValidator.validateSeq(paymentSeq);

    paymentDeleteService.delete(paymentSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 결제 단일 상세 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/{paymentSeq}")
  public ResponseEntity<?> doGet(@PathVariable("paymentSeq") Long paymentSeq) {
    PaymentValidator.validateSeq(paymentSeq);

    return ResponseEntity.ok(new DataResponse(paymentOneService.getDetail(paymentSeq)));
  }

  /**
   * 일일 결제 내역 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/daily")
  public ResponseEntity<?> doGetDaily(@RequestParam(required = false) String date) {
    PaymentValidator.validateDaily(date);

    return ResponseEntity.ok(new ListResponse(paymentListService.getDailyList(date)));
  }

  /**
   * 월별 결제 내역 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/monthly")
  public ResponseEntity<?> doGetMonthly(@RequestParam(required = false) Integer year,
      @RequestParam(required = false) Integer month) {
    PaymentValidator.validateMonthly(year, month);

    return ResponseEntity.ok(new ListResponse(paymentListService.getMonthlyList(year, month)));
  }

  /**
   * 결제 내용 검색 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/search")
  public ResponseEntity<?> doGetSearch(@RequestParam(required = false) String keyword) {
    return ResponseEntity.ok(new ListResponse(paymentListService.getSearchList(keyword)));
  }

  /**
   * 결제 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/stats")
  public ResponseEntity<?> doGetStats(@ModelAttribute PaymentStatsRequest param) {
    PaymentValidator.validate(param);

    return ResponseEntity.ok(new DataResponse(paymentListService.getList(param)));
  }

  /**
   * 결제 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestPart PaymentEntryRequest param, @RequestPart MultipartFile image) {
    PaymentValidator.validate(param);

    paymentEntryService.entry(param, image);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 결제 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody PaymentModifyRequest param) {
    PaymentValidator.validate(param);

    paymentModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
