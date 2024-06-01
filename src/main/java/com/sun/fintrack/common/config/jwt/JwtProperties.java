package com.sun.fintrack.common.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secretKey, int expirationTime, String cookieName) {

}
