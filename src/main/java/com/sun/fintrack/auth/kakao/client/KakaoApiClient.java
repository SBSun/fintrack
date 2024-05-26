package com.sun.fintrack.auth.kakao.client;

import com.sun.fintrack.auth.kakao.KakaoOAuthConfig;
import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;
import com.sun.fintrack.auth.kakao.response.KakaoTokenResponse;
import com.sun.fintrack.common.exception.ValidationException;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

/**
 * Kakao Api 요청
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Component
public class KakaoApiClient {

  private final KakaoOAuthConfig kakaoOAuthConfig;

  /**
   * Kakao 엑세스 토큰 발급
   *
   * @param authCode 경도
   * @return Kakao 엑세스 토큰
   */
  public KakaoTokenResponse getToken(String authCode) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", kakaoOAuthConfig.clientId());
    params.add("redirect_uri", kakaoOAuthConfig.redirectUri());
    params.add("code", authCode);
    params.add("client_secret", kakaoOAuthConfig.clientSecret());

    String resultText = WebClient.create(kakaoOAuthConfig.tokenUri())
                                 .post()
                                 .bodyValue(params)
                                 .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                                 .exchangeToMono(res -> res.bodyToMono(String.class))
                                 .block();

    return makeTokenInfo(resultText);
  }

  public KakaoMemberResponse getUser(String bearerToken) {
    String resultText = WebClient.create(kakaoOAuthConfig.userInfoUri())
                                 .get()
                                 .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                                 .header(HttpHeaders.AUTHORIZATION, bearerToken)
                                 .exchangeToMono(res -> res.bodyToMono(String.class))
                                 .block();

    return makeUserInfo(resultText);
  }

  /**
   * json 문자열 파싱 - 토큰
   *
   * @param resultText 응답 문자열
   * @return 토큰 정보
   */
  private KakaoTokenResponse makeTokenInfo(String resultText) {
    try {
      JSONObject jsonObject = new JSONObject(resultText);
      return new KakaoTokenResponse(jsonObject.getString("token_type"), jsonObject.getString("access_token"),
          jsonObject.getInt("expires_in"), jsonObject.getString("refresh_token"),
          jsonObject.getInt("refresh_token_expires_in"));
    } catch (Exception e) {
      throw new ValidationException("auth.oauth_token_fail");
    }
  }

  /**
   * json 문자열 파싱 - 회원 정보
   *
   * @param resultText 응답 문자열
   * @return 회원 정보
   */
  private KakaoMemberResponse makeUserInfo(String resultText) {
    try {
      JSONObject kakaoAccount = new JSONObject(resultText).getJSONObject("kakao_account");
      return new KakaoMemberResponse(kakaoAccount.getString("email"),
          kakaoAccount.getJSONObject("profile").getString("nickname"));
    } catch (Exception e) {
      throw new ValidationException("auth.oauth_user_fail");
    }
  }
}
