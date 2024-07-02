package com.sun.fintrack.auth.service;

import com.sun.fintrack.auth.domain.client.SocialMemberClientComposite;
import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.domain.provider.AuthCodeRequestUrlProviderComposite;
import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;
import com.sun.fintrack.category.command.service.CategoryEntryService;
import com.sun.fintrack.common.domain.enums.Valid;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import lombok.RequiredArgsConstructor;

/**
 * OAuth 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProvider;
  private final SocialMemberClientComposite socialUserClient;

  private final JwtService jwtService;

  private final CategoryEntryService categoryEntryService;

  private final MemberRepository memberRepository;

  /**
   * 소셜 타입에 따른 AuthCode 요청 Url 반환
   *
   * @param socialType 소셜 타입
   * @return AuthCode 요청 Url
   */
  public String getAuthCodeRequestUrl(SocialType socialType) {
    return authCodeRequestUrlProvider.provide(socialType);
  }

  /**
   * 소셜 로그인, 가입 전이라면 강제 회원가입
   *
   * @param socialType 소셜 타입
   * @param code       인가 코드
   */
  @Transactional
  public String login(SocialType socialType, String code) {
    KakaoMemberResponse memberInfo = socialUserClient.fetch(socialType, code);

    Member member = memberRepository.findByEmailAndValid(memberInfo.email(), Valid.TRUE).orElse(null);
    if (Objects.isNull(member)) {
      member = memberRepository.save(new Member(memberInfo));
      // 기본 카테고리 등록
      categoryEntryService.entry(member);
    }

    return jwtService.generateToken(member);
  }
}
