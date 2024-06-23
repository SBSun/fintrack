package com.sun.fintrack.category.command.service;

import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.query.dao.CategoryOneDao;
import com.sun.fintrack.category.query.repository.CategoryRepository;
import com.sun.fintrack.category.request.CategoryEntryRequest;
import com.sun.fintrack.common.utils.CsvReadUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class CategoryEntryService {

  private final CategoryRepository categoryRepository;
  private final CategoryOneDao categoryOneDao;

  private final MemberOneService memberOneService;

  /**
   * 카테고리 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(CategoryEntryRequest param) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());

    TradeType type = TradeType.fromCode(param.getType());
    int order = categoryOneDao.selectTopOrder(type) + 1;

    categoryRepository.save(new Category(param.getName(), order, type, member));
  }

  /**
   * 기본 카테고리 등록
   */
  @Transactional
  public void entry(Member member) {
    List<Category> categoryList = new ArrayList<>();

    List<String[]> lines = CsvReadUtils.readCSVFile("category.csv");
    lines.forEach(line -> {
      String name = line[0];
      Integer order = Integer.parseInt(line[1]);
      TradeType type = TradeType.fromCode(line[2]);

      categoryList.add(new Category(name, order, type, member));
    });

    categoryRepository.saveAll(categoryList);
  }
}
