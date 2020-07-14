package com.ryanlindeborg.hourglass.hourglassspring.security;

import com.ryanlindeborg.hourglass.hourglassspring.model.OAuthProviderType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
// This implements the Spring FunctionalInterface for how to grab OAuth user ID from the user authentication token
public class OAuthUserMapperRegistry {
    private Map<OAuthProviderType, OAuthUserToIDMapper> mappers = new HashMap<>() {{
       put(OAuthProviderType.GITHUB, user -> user.getPrincipal().getAttribute("id").toString());
    }};

    public OAuthUserToIDMapper getMapper(OAuthProviderType providerType)  {
        return mappers.get(providerType);
    }
}
