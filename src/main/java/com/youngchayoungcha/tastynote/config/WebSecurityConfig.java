package com.youngchayoungcha.tastynote.config;

import com.youngchayoungcha.tastynote.util.JwtUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // Spring Security로 인해 생긴 문제를 해결하기 위해 우선 CSRF 비 활성화
//        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
//    }

// swagger 관련 리소스 시큐리티 필터 제거 필요. 제대로 이해는 못했기 때문에 공부해봐야함.
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(
//        "/v2/api-docs", "/swagger-resource/**",
//        "/swagger-ui.html", "/webjars/**", "/swagger/**", "/api/**", "/**);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/token/*").permitAll()
                .antMatchers("/token/*/hello").authenticated()
                .and()
                .addFilterBefore(new JwtAuthFilter(this.jwtUtils), UsernamePasswordAuthenticationFilter.class);
    }
}
