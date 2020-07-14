package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.OAuthProviderType;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import com.ryanlindeborg.hourglass.hourglassspring.security.OAuthUserMapperRegistry;
import com.ryanlindeborg.hourglass.hourglassspring.security.OAuthUserToIDMapper;
import com.ryanlindeborg.hourglass.hourglassspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class OAuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private OAuthUserMapperRegistry oAuthUserMapperRegistry;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/oauth2/end")
    public String issueJwtAndSetInCookies(HttpServletResponse response, HttpServletRequest request) {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuthProviderType providerType = OAuthProviderType.fromSpringRegistrationId(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
        OAuthUserToIDMapper mapper = oAuthUserMapperRegistry.getMapper(providerType);

        String oauthUserId = mapper.getId(oAuth2AuthenticationToken);

        User user = userRepository.getUserByOauthProviderTypeAndOauthUserId(providerType, oauthUserId);

        if (user == null) {
            // TODO: Implement user registration workflow for user registration with OAuth
            throw new AccessDeniedException("OAuth user is authenticated but not registered with Hourglass");
        }

        String jwt = userService.issueJWT(user.getDisplayName());

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("hourglass_token", jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // Returns view via Thymeleaf
        return "end_oauth";

    }
}
