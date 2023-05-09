package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.LoginSuccessHandelr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration // '이것은 환경설정 파일' 이라는 의미 
@EnableWebSecurity // '시큐리티를 사용할 것이다'라는 의미
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    
    final UserDetailsService userDetailsService;

    @Bean // 객체를 생성함
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain");
        
        // 권한 설정
        // http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().antMatchers("/customer/join.do").permitAll();

        // 403 페이지 설정 (접근 권한 불가 시 표시할 화면 )
        http.exceptionHandling().accessDeniedPage("/403page.do");

        // 로그인 처리
        http.formLogin()
            .loginPage("/customer/login.do")
            .loginProcessingUrl("/customer/loginaction.do")
            .usernameParameter("id")
            .passwordParameter("pw")
            // .defaultSuccessUrl("/customer/home.do")
            .successHandler(new LoginSuccessHandelr())
            .permitAll();

        // 로그아웃 처리
        http.logout()
            .logoutUrl("/customer/logout.do")
            .logoutSuccessUrl("/customer/home.do")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll();

        // 서비스 등록
        http.userDetailsService(userDetailsService);

        return http.build();
    }

    // 정적 자원에는 시큐리티 필터 규칙을 적용하지 않도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 회원가입과 로그인에서 사용하는 암호화 알고리즘은 동일한 것을 사용해야한다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
