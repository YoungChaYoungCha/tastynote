package com.youngchayoungcha.tastynote.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //참고 코드에서는 private으로 안했던데 왜인지 하면서 파악할 것
    public final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Spring Security로 인해 생긴 문제를 해결하기 위해 우선 CSRF 비 활성화
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }
}
