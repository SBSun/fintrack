package com.sun.fintrack.auth.domain.client;

import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;
import com.sun.fintrack.common.exception.ValidationException;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * Resource Server 회원 정보 요청
 *
 * @author 송병선
 */
@Component
public class SocialMemberClientComposite {

  private final Map<SocialType, SocialMemberClient> clientMap;

  public SocialMemberClientComposite(Set<SocialMemberClient> clients) {
    clientMap = clients.stream().collect(toMap(SocialMemberClient::supportSocial, identity()));
  }

  /**
   * 소셜 타입에 맞는 회원 정보 반환
   *
   * @param socialType 소셜 타입
   * @return 회원 정보
   */
  public KakaoMemberResponse fetch(SocialType socialType, String authCode) {
    return getClient(socialType).fetch(authCode);
  }

  private SocialMemberClient getClient(SocialType socialType) {
    return Optional.ofNullable(clientMap.get(socialType))
                   .orElseThrow(() -> new ValidationException("auth.param_social_type_invalid"));
  }
}
