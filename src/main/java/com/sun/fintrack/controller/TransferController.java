package com.sun.fintrack.controller;

import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.transfer.command.service.TransferDeleteService;
import com.sun.fintrack.transfer.command.service.TransferEntryService;
import com.sun.fintrack.transfer.command.service.TransferModifyService;
import com.sun.fintrack.transfer.query.service.TransferOneService;
import com.sun.fintrack.transfer.request.TransferEntryRequest;
import com.sun.fintrack.transfer.request.TransferModifyRequest;
import com.sun.fintrack.validation.TransferValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 이체 api
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/transfers")
@RestController
public class TransferController {

  private final TransferEntryService transferEntryService;
  private final TransferModifyService transferModifyService;
  private final TransferDeleteService transferDeleteService;

  private final TransferOneService transferOneService;

  /**
   * 이체 삭제
   *
   * @param transferSeq 이체 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/{transferSeq}")
  public ResponseEntity<?> doDelete(@PathVariable("transferSeq") Long transferSeq) {
    TransferValidator.validateSeq(transferSeq);

    transferDeleteService.delete(transferSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 이체 단일 상세 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/{transferSeq}")
  public ResponseEntity<?> doGet(@PathVariable("transferSeq") Long transferSeq) {
    TransferValidator.validateSeq(transferSeq);

    return ResponseEntity.ok(new DataResponse(transferOneService.getDetail(transferSeq)));
  }

  /**
   * 이체 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestBody TransferEntryRequest param) {
    TransferValidator.validate(param);

    transferEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 이체 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody TransferModifyRequest param) {
    TransferValidator.validate(param);

    transferModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
