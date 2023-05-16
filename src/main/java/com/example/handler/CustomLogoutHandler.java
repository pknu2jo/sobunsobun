package com.example.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        // throw new UnsupportedOperationException("Unimplemented method 'onLogoutSuccess'");

        log.info( "로그아웃 핸들러의 authentication => {}", authentication.toString() );

        String role = authentication.getAuthorities().toArray()[0].toString();

        log.info("로그아웃 핸들러의 role => {}", role);

        if( role.equals("ROLE_CUSTOMER") ) { 
            response.sendRedirect( request.getContextPath() + "/customer/home.do" );
        } else if ( role.equals("ROLE_SELLER") ) { 
            response.sendRedirect( request.getContextPath() + "/seller/login.do" );
        } else if ( role.equals("ROLE_ADMIN") ) { 
            response.sendRedirect( request.getContextPath() + "/admin/login.do" );
        } else {
            response.sendRedirect(request.getContextPath() + "/customer/home.do");
        }
    }
    
}
