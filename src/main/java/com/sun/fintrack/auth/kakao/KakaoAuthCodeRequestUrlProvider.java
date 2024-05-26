package com.sun.fintrack.auth.kakao;

import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.domain.provider.AuthCodeRequestUrlProvider;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

/**
 * 카카오 AuthCode 요청 Url 제공
 *
 * @author 송병선
 */
@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

  private final KakaoOAuthConfig kakaoOAuthConfig;

  @Override
  public String provideUrl() {
    return UriComponentsBuilder.fromUriString(kakaoOAuthConfig.authorizeUri())
                               .queryParam("response_type", "code")
                               .queryParam("client_id", kakaoOAuthConfig.clientId())
                               .queryParam("redirect_uri", kakaoOAuthConfig.redirectUri())
                               .queryParam("scope", String.join(",", kakaoOAuthConfig.scope()))
                               .toUriString();
  }

  @Override
  public SocialType supportSocial() {
    return SocialType.KAKAO;
  }
}
