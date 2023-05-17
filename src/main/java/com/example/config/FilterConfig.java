package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.filter.UrlFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FilterConfig {
    
    // 토큰 검증 필터
    // @Bean // 서버가 구동될 때 자동으로 호출됨
    // public FilterRegistrationBean<JwtFilter2> filterRegistrationBean(JwtFilter2 jwtFilter) {

    //     log.info("filter => {}", "filterConfig");

    //     FilterRegistrationBean<JwtFilter2> filterReg = new FilterRegistrationBean<>();
    //     filterReg.setFilter(jwtFilter);

    //     filterReg.addUrlPatterns("/api/student2/update.json");
    //     filterReg.addUrlPatterns("/api/student2/delete.json");
        
    //     // filterReg.addUrlPatterns("/api/student2/*"); // *은 전체 url을 뜻함
    //     return filterReg;
    // }

    // 로그인 후 현재 페이지 불러오기 필터
    @Bean // 서버가 구동될 때 자동으로 호출됨
    public FilterRegistrationBean<UrlFilter> filterRegistrationBean1(UrlFilter filter) {

        log.info("filter => {}", "filterConfig");

        FilterRegistrationBean<UrlFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setFilter(filter);
        
        filterReg.addUrlPatterns("/api/student2/*"); // *은 전체 url을 뜻함
        // filterReg.

        return filterReg;
    }
}
