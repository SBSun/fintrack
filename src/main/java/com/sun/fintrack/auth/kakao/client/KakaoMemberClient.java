package com.sun.fintrack.auth.kakao.client;

import com.sun.fintrack.auth.domain.client.SocialMemberClient;
import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.kakao.KakaoOAuthConfig;
import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;
import com.sun.fintrack.auth.kakao.response.KakaoTokenResponse;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Kakao 회원 정보 요청
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Component
public class KakaoMemberClient implements SocialMemberClient {

  private final KakaoApiClient kakaoApiClient;
  private final KakaoOAuthConfig kakaoOAuthConfig;

  @Override
  public KakaoMemberResponse fetch(String authCode) {
    KakaoTokenResponse tokenInfo = kakaoApiClient.getToken(authCode);
    return kakaoApiClient.getUser(kakaoOAuthConfig.authorizationPrefix() + tokenInfo.accessToken());
  }

  @Override
  public SocialType supportSocial() {
    return SocialType.KAKAO;
  }
}
