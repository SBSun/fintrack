package com.sun.fintrack.trade.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.aws.service.AwsS3Service;
import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.query.service.CategoryOneService;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;
import com.sun.fintrack.trade.domain.Trade;
import com.sun.fintrack.trade.domain.enums.TradeType;
import com.sun.fintrack.trade.query.repository.TradeRepository;
import com.sun.fintrack.trade.request.TradeEntryRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import lombok.RequiredArgsConstructor;

/**
 * 거래 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class TradeEntryService {

  private final AwsS3Service awsS3Service;

  private final TradeRepository tradeRepository;

  private final CategoryOneService categoryOneService;
  private final AssetOneService assetOneService;
  private final MemberOneService memberOneService;

  /**
   * 거래 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(TradeEntryRequest param, MultipartFile image) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());
    Asset asset = assetOneService.getOne(param.getAssetSeq());
    TradeType type = TradeType.fromCode(param.getType());
    Category category = categoryOneService.getOne(param.getCategorySeq(), type);
    String imagePath =
        Objects.nonNull(image) && !image.isEmpty() ? awsS3Service.upload(MemberUtils.getMemberSeq(), "trade", image) :
            null;

    tradeRepository.save(
        new Trade(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getTradeDt()), type,
            imagePath, member, asset, category));
  }
}
