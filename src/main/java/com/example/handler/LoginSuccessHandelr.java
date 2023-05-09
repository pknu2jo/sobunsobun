package com.example.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginSuccessHandelr implements AuthenticationSuccessHandler {
    // 로그인 시 내가 원하는 곳으로 가게 하려면 handler를 사용해야함
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        log.info("LoginSuccessandelr => {}", authentication.toString());


        response.sendRedirect(request.getContextPath() + "/customer/home.do");
    }

   
}
