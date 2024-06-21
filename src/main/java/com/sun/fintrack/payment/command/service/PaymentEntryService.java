package com.sun.fintrack.payment.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.aws.service.AwsS3Service;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;
import com.sun.fintrack.payment.domain.Payment;
import com.sun.fintrack.payment.domain.PaymentCategory;
import com.sun.fintrack.payment.query.repository.PaymentRepository;
import com.sun.fintrack.payment.query.service.PaymentOneService;
import com.sun.fintrack.payment.request.PaymentEntryRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import lombok.RequiredArgsConstructor;

/**
 * 결제 정보 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class PaymentEntryService {

  private final AwsS3Service awsS3Service;

  private final PaymentRepository paymentRepository;
  private final PaymentOneService paymentOneService;

  private final AssetOneService assetOneService;

  private final MemberOneService memberOneService;

  /**
   * 결제 정보 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(PaymentEntryRequest param, MultipartFile image) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());
    Asset asset = assetOneService.getOne(param.getAssetSeq());
    PaymentCategory category = paymentOneService.getCategory(param.getCategoryId());
    String imagePath =
        Objects.nonNull(image) && !image.isEmpty() ? awsS3Service.upload(MemberUtils.getMemberSeq(), "payment", image) :
            null;

    paymentRepository.save(new Payment(param.getContent(), param.getPrice(), imagePath,
        DateTimeUtils.convertToDateTime(param.getPaymentDt()), member, asset, category));
  }
}
