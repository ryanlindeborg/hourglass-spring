package com.ryanlindeborg.hourglass.hourglassspring.security;

import com.ryanlindeborg.hourglass.hourglassspring.model.security.RevokedToken;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.security.RevokedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtAuthenticationFilter implements Filter {
    // My custom header name for auth
    public final static String JWT_HEADER_NAME = "X-Auth-Token";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HourglassUserDetailsService userDetailsService;

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(JWT_HEADER_NAME);

        if (token != null && !token.trim().isEmpty()) {
            try {
                this.authenticate(token);
                LOGGER.warn("JWT authentication passed");
            }
            catch (Throwable e){
                throw new AccessDeniedException("Access Denied.", e);
            }
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String token) {
        JwtParser tokenParser = Jwts.parserBuilder().setSigningKey(jwtService.getJWTSecret()).build();
        Jws<Claims> claimsJws = tokenParser.parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        String displayName = claims.getSubject();

        // Token expiration check
        Date expirationDate = claims.getExpiration();
        Date currentDate = new Date();
        if (expirationDate.before(currentDate)) {
            throw new AccessDeniedException("Token has expired");
        }

        // This UserDetailsService implementation requires displayName, not username
        HourglassUser hourglassUser = userDetailsService.loadHourglassUserByDisplayName(displayName);

        // Check if token was issued before a revocation action (i.e., it should no longer be valid)
        if (hourglassUser.getMinJwtIssuedTimestamp() != null && hourglassUser.getMinJwtIssuedTimestamp() > claims.getIssuedAt().getTime()) {
            throw new AccessDeniedException("Token has been revoked");
        }

        // Check if token is a revoked token (via Revoked Token database entity)
        RevokedToken revokedToken = revokedTokenRepository.getRevokedTokenByToken(token);
        if (revokedToken != null) {
            throw new AccessDeniedException("Token has been revoked");
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(hourglassUser,
                        "HIDDEN", hourglassUser.getAuthorities()));


    }
}
