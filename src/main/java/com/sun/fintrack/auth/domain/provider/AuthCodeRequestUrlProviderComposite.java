package com.sun.fintrack.auth.domain.provider;

import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.common.exception.ValidationException;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * AuthCode 요청 Url 제공
 *
 * @author 송병선
 */
@Component
public class AuthCodeRequestUrlProviderComposite {

  private final Map<SocialType, AuthCodeRequestUrlProvider> providerMap;

  public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers) {
    providerMap = providers.stream().collect(toMap(AuthCodeRequestUrlProvider::supportSocial, identity()));
  }

  /**
   * 소셜 타입에 맞는 url 반환
   *
   * @param socialType 소셜 타입
   * @return authcode 요청 url
   */
  public String provide(SocialType socialType) {
    return getProvider(socialType).provideUrl();
  }

  private AuthCodeRequestUrlProvider getProvider(SocialType socialType) {
    return Optional.ofNullable(providerMap.get(socialType))
                   .orElseThrow(() -> new ValidationException("auth.param_social_type_invalid"));
  }
}
