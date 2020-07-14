package com.ryanlindeborg.hourglass.hourglassspring.security;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@FunctionalInterface
public interface OAuthUserToIDMapper {
    String getId(OAuth2AuthenticationToken user);
}
