package com.ryanlindeborg.hourglass.hourglassspring.security;

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

    private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private HourglassUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(JWT_HEADER_NAME);

        if (token != null && !token.trim().isEmpty()) {
            try {
                this.authenticate(token);
                logger.warn("JWT authentication passed");
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
        if (hourglassUser.getMinJwtIssuedTimestamp() != null && hourglassUser.getMinJwtIssuedTimestamp() > claims.getIssuedAt().getTime()) {
            throw new AccessDeniedException("Token has been revoked");
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(hourglassUser,
                        "HIDDEN", Collections.singleton(new SimpleGrantedAuthority("USER"))));


    }
}
