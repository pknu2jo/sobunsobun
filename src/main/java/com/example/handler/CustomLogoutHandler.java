package com.example.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        log.info( "로그아웃 핸들러의 authentication => {}", authentication.toString() );

        String role = authentication.getAuthorities().toArray()[0].toString();

        log.info("로그아웃 핸들러의 role => {}", role);
        
        // 이전 페이지 정보 가져오기
        HttpSession httpSession = request.getSession();
        String backUrl = (String) httpSession.getAttribute("url");
        
        log.info("로그아웃 핸들러의 backUrl => {}", backUrl);

        if( role.equals("CUSTOMER") ) { 
            response.sendRedirect( request.getContextPath() + "/customer/home.do" );
            // response.sendRedirect( request.getContextPath() + backUrl );
            // backUrl은 안됩니다..왜냐면 로그아웃 누르면서 세션이 다 날아가기 때문ㅇ...
        } else if ( role.equals("SELLER") ) { 
            response.sendRedirect( request.getContextPath() + "/seller/login.do" );
        } else if ( role.equals("ADMIN") ) { 
            response.sendRedirect( request.getContextPath() + "/admin/login.do" );
        } else {
            response.sendRedirect(request.getContextPath() + "/customer/home.do");
        }
    }
    
}
