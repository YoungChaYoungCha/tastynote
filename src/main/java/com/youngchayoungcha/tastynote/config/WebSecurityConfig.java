package com.youngchayoungcha.tastynote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Spring Security로 인해 생긴 문제를 해결하기 위해 우선 CSRF 비 활성화
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }
}
