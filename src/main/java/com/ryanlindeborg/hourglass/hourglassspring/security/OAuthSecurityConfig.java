package com.ryanlindeborg.hourglass.hourglassspring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@Order(1)
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/oauth2/**", "/login/oauth2/**")
                .and()
                .authorizeRequests().antMatchers("/oauth2/end").authenticated().and()
                .oauth2Login().successHandler(new SimpleUrlAuthenticationSuccessHandler("/oauth2/end"));
    }
}
