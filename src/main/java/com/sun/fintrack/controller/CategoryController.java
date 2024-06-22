package com.sun.fintrack.controller;

import com.sun.fintrack.category.command.service.CategoryDeleteService;
import com.sun.fintrack.category.command.service.CategoryEntryService;
import com.sun.fintrack.category.command.service.CategoryModifyService;
import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.query.service.CategoryListService;
import com.sun.fintrack.category.request.CategoryEntryRequest;
import com.sun.fintrack.category.request.CategoryModifyRequest;
import com.sun.fintrack.common.response.ListResponse;
import com.sun.fintrack.common.response.SuccessResponse;
import com.sun.fintrack.validation.CategoryValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/categories")
@RestController
public class CategoryController {

  private final CategoryListService categoryListService;

  private final CategoryEntryService categoryEntryService;
  private final CategoryModifyService categoryModifyService;
  private final CategoryDeleteService categoryDeleteService;

  /**
   * 카테고리 삭제
   *
   * @param categoryId 카테고리 ID
   * @return 요청 결과
   */
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<?> doDelete(@PathVariable("categoryId") Long categoryId,
      @RequestParam(required = false) String type) {
    CategoryValidator.validate(categoryId, type);

    categoryDeleteService.delete(categoryId, type);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 카테고리 목록 조회
   *
   * @return 요청 결과
   */
  @GetMapping
  public ResponseEntity<?> doGetList(@RequestParam(required = false) String type) {
    CategoryValidator.validateType(type);

    return ResponseEntity.ok(new ListResponse(categoryListService.getList(CategoryType.fromCode(type))));
  }

  /**
   * 카테고리 등록
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PostMapping
  public ResponseEntity<?> doPost(@RequestBody CategoryEntryRequest param) {
    CategoryValidator.validate(param);

    categoryEntryService.entry(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 카테고리 수정
   *
   * @param param 요청 파라미터
   * @return 요청 결과
   */
  @PutMapping
  public ResponseEntity<?> doPut(@RequestBody CategoryModifyRequest param) {
    CategoryValidator.validate(param);

    categoryModifyService.modify(param);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
