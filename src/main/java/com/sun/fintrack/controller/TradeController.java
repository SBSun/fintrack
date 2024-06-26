package com.sun.fintrack.controller;

import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.trade.command.service.TradeDeleteService;
import com.sun.fintrack.trade.command.service.TradeEntryService;
import com.sun.fintrack.trade.command.service.TradeModifyService;
import com.sun.fintrack.trade.query.service.TradeOneService;
import com.sun.fintrack.trade.request.TradeEntryRequest;
import com.sun.fintrack.trade.request.TradeModifyRequest;
import com.sun.fintrack.trade.request.TradeStatsRequest;
import com.sun.fintrack.validation.TradeValidator;

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

/**
 * 거래 내역 api
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/trades")
@RestController
public class TradeController {

  private final TradeEntryService tradeEntryService;
  private final TradeModifyService tradeModifyService;
  private final TradeDeleteService tradeDeleteService;

  private final TradeOneService tradeOneService;

  /**
   * 거래 삭제
   *
   * @param tradeSeq 거래 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/{tradeSeq}")
  public ResponseEntity<?> doDelete(@PathVariable("tradeSeq") Long tradeSeq,
      @RequestParam(required = false) String type) {
    TradeValidator.validate(tradeSeq, type);

    tradeDeleteService.delete(tradeSeq, type);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 거래 단일 상세 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/{tradeSeq}")
  public ResponseEntity<?> doGet(@PathVariable("tradeSeq") Long tradeSeq, @RequestParam(required = false) String type) {
    TradeValidator.validate(tradeSeq, type);

    return ResponseEntity.ok(new DataResponse(tradeOneService.getDetail(tradeSeq, type)));
  }

  /**
   * 거래 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/stats")
  public ResponseEntity<?> doGetStats(@ModelAttribute TradeStatsRequest param) {
    TradeValidator.validate(param);

    return ResponseEntity.ok(new DataResponse(tradeOneService.getOne(param)));
  }

  /**
   * 거래 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestPart TradeEntryRequest param, @RequestPart MultipartFile image) {
    TradeValidator.validate(param);

    tradeEntryService.entry(param, image);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 거래 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody TradeModifyRequest param) {
    TradeValidator.validate(param);

    tradeModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
