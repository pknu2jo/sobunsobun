package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.CustomLogoutHandler;
import com.example.service.SecurityAdminSeviceImpl;
import com.example.service.SecurityCustomerSeviceImpl;
import com.example.service.SecuritySellerSeviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration // '이것은 환경설정 파일' 이라는 의미 
@EnableWebSecurity // '시큐리티를 사용할 것이다'라는 의미
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    
    final SecurityCustomerSeviceImpl userCustomerDetailsService;
    final SecuritySellerSeviceImpl userSellerDetailsService;
    final SecurityAdminSeviceImpl userAdminDetailsService;
    
    @Bean // 객체를 생성함
    @Order(value = 1) // 관리자
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain1");

        http.antMatcher("/admin/login.do")
        .antMatcher("/admin/loginaction.do")
        .authorizeRequests().anyRequest().authenticated().and();

        // 관리자 로그인 처리
        http.formLogin()
            .loginPage("/admin/login.do")
            .loginProcessingUrl("/admin/loginaction.do")
            .usernameParameter("id")
            .passwordParameter("pw")
            .defaultSuccessUrl("/admin/home.do")
            .permitAll();

        

        // 서비스 등록
        http.userDetailsService(userAdminDetailsService);

        return http.build();
    }

    @Bean // 객체를 생성함
    @Order(value = 2) // 업체
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {

        log.info("SecurityConfig => {}", "start filter chain2");

        http.antMatcher("/seller/login.do")
        .antMatcher("/seller/loginaction.do")
        .authorizeRequests().anyRequest().authenticated().and();

        // 업체 로그인 처리
        http.formLogin()
            .loginPage("/seller/login.do")
            .loginProcessingUrl("/seller/loginaction.do")
            .usernameParameter("no")
            .passwordParameter("pw")
            .defaultSuccessUrl("/seller/home.do")
            .permitAll();

        // 서비스 등록
        http.userDetailsService(userSellerDetailsService);

        return http.build();
    }

    @Bean // 객체를 생성함
    @Order(value = 3) // 고객
    public SecurityFilterChain filterChain3(HttpSecurity http) throws Exception {

        log.info("SecurityConfig => {}", "start filter chain3");
        
        // 권한 설정
        // http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests()
            .antMatchers("/customer/join.do", "/customer/home.do", "/customer/login.do","/customer/findid.do","/customer/findidok.do", "/customer/findpw.do", "/customer/findpwok.do", "/customer/seimage", "/customer/kakaojoin.do", "/customer/kakaojoinaction.do", "/customer/kmtest.do").permitAll()
            .antMatchers("/seller/join.do", "/seller/item/insert.do").permitAll()
            .antMatchers("/admin/join.do").permitAll()

            .antMatchers("/admin", "/admin/*").hasAuthority("ADMIN")  // 주소가 9090/ROOT/admin  ~~ 모든것
            .antMatchers("/seller", "/seller/*").hasAnyAuthority("ADMIN", "SELLER")
            .antMatchers("/customer", "/customer/*").hasAnyAuthority("CUSTOMER")

            .anyRequest().permitAll();


        // 403 페이지 설정 (접근 권한 불가 시 표시할 화면 )
        http.exceptionHandling().accessDeniedPage("/error/403page.do");

        // 로그인 처리
        http.formLogin()
            .loginPage("/customer/login.do")
            .loginProcessingUrl("/customer/loginaction.do")
            .usernameParameter("id")
            .passwordParameter("pw")
            .defaultSuccessUrl("/customer/home.do")
            .permitAll();

        // 로그아웃 처리 (고객, 업체, 관리자 모두 해당)
        http.logout()
            .logoutUrl("/logout.do")
            .logoutSuccessHandler(new CustomLogoutHandler())
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll();

        //post는 csrf를 전송해야하지만, 주소가 /api로 시작하는 모든url은  csrf가 없어도 됨
        http.csrf().ignoringAntMatchers("/api/**");

        // 서비스 등록
        http.userDetailsService(userCustomerDetailsService);

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
