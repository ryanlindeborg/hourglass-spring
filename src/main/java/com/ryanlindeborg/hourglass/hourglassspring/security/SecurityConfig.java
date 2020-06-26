package com.ryanlindeborg.hourglass.hourglassspring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
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
        //TODO: Change these urls to reference app property for api version so don't have to update that all over when change api version
        http.csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/user/session").anonymous().and()
                .authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/user/registration").anonymous().and()
                .authorizeRequests().anyRequest().authenticated().and()
                .addFilterAfter(jwtAuthenticationFilter, ExceptionTranslationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler);
    }
}
