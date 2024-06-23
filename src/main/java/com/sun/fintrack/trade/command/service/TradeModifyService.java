package com.sun.fintrack.trade.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.query.service.CategoryOneService;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.trade.domain.Trade;
import com.sun.fintrack.trade.domain.enums.TradeType;
import com.sun.fintrack.trade.query.service.TradeOneService;
import com.sun.fintrack.trade.request.TradeModifyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 거래 수정 서비스
 */
@RequiredArgsConstructor
@Service
public class TradeModifyService {

  private final TradeOneService tradeOneService;
  private final CategoryOneService categoryOneService;

  private final AssetOneService assetOneService;

  /**
   * 거래 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(TradeModifyRequest param) {
    TradeType type = TradeType.fromCode(param.getType());
    Trade trade = tradeOneService.getOne(param.getTradeSeq(), type);
    Asset asset = assetOneService.getOne(param.getAssetSeq());
    Category category = trade.getCategory();
    if (!category.getCategorySeq().equals(param.getCategorySeq())) {
      category = categoryOneService.getOne(param.getCategorySeq(), type);
    }

    trade.modify(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getTradeDt()), type, asset,
        category);
  }
}