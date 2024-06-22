package com.sun.fintrack.payment.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.query.repository.CategoryRepository;
import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.payment.domain.Payment;
import com.sun.fintrack.payment.query.service.PaymentOneService;
import com.sun.fintrack.payment.request.PaymentModifyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 결제 정보 수정 서비스
 */
@RequiredArgsConstructor
@Service
public class PaymentModifyService {

  private final PaymentOneService paymentOneService;
  private final CategoryRepository categoryRepository;

  private final AssetOneService assetOneService;

  /**
   * 결제 정보 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(PaymentModifyRequest param) {
    Payment payment = paymentOneService.getOne(param.getPaymentSeq());
    Asset asset = assetOneService.getOne(param.getAssetSeq());
    Category category = payment.getCategory();
    if (!category.getCategorySeq().equals(param.getCategorySeq())) {
      category = categoryRepository.findById(param.getCategorySeq())
                                   .orElseThrow(() -> new ValidationException("payment.category.not_found"));
    }

    payment.modify(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getPaymentDt()), asset,
        category);
  }
}