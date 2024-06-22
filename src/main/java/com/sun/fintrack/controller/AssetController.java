package com.sun.fintrack.controller;

import com.sun.fintrack.asset.command.service.AssetDeleteService;
import com.sun.fintrack.asset.command.service.AssetEntryService;
import com.sun.fintrack.asset.command.service.AssetModifyService;
import com.sun.fintrack.asset.query.service.AssetListService;
import com.sun.fintrack.asset.request.AssetEntryRequest;
import com.sun.fintrack.asset.request.AssetModifyRequest;
import com.sun.fintrack.common.response.ListResponse;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.validation.AssetValidator;

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

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/assets")
@RestController
public class AssetController {

  private final AssetListService assetListService;

  private final AssetEntryService assetEntryService;
  private final AssetModifyService assetModifyService;
  private final AssetDeleteService assetDeleteService;

  /**
   * 자산 삭제
   *
   * @param assetSeq 자산 일련번호
   * @return 요청 결과
   */
  @DeleteMapping("/{assetSeq}")
  public ResponseEntity<?> doDelete(@PathVariable(value = "assetSeq", required = false) Long assetSeq) {
    AssetValidator.validateSeq(assetSeq);

    assetDeleteService.delete(assetSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 자산 목록 조회
   *
   * @return 요쳥 결과
   */
  @GetMapping
  public ResponseEntity<?> doGetList() {
    return ResponseEntity.ok(new ListResponse(assetListService.getList()));
  }

  /**
   * 자산 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestBody AssetEntryRequest param) {
    AssetValidator.validate(param);

    assetEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 자산 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody AssetModifyRequest param) {
    AssetValidator.validate(param);

    assetModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
