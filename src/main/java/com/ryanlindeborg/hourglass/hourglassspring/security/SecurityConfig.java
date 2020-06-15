package com.ryanlindeborg.hourglass.hourglassspring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private HourglassAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private HourglassAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disabling CSRF for now
        http.csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/user/session").anonymous().and()
                //TODO: Also add register url to URI that can be accessed by anonymous user
                .authorizeRequests().anyRequest().authenticated().and()
                .addFilterAfter(jwtAuthenticationFilter, ExceptionTranslationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler);
    }
}
