package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 로그인 후에 이전페이지로 돌아가기 위해 현재의 페이지를 저장하는 필터
// 현재 페이지 중에서 로그인, 로그아웃은 제외시켜야함
@Component
@Slf4j
@RequiredArgsConstructor
public class UrlFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // ex) /ROOT/api/student2/update.json?id=a
            String contextPath = request.getContextPath();  // contextPath == /ROOT 
            String path = request.getServletPath();         // path == /api/student2/update.json
            String query = request.getQueryString();        //  query  == id=a

            log.info("왜 query 안찍혀.. {}, {}, {}", contextPath, path, query);

            // url에 login, logout이 포함되지 않는 경우에만 보관
            // 다른 경우가 또 뭐가있지..?

            if( !path.contains("login")  && !path.contains("logout") && !path.contains("image") && !path.contains("join")) {
                HttpSession httpSession = request.getSession();                

                if(query == null) {
                    log.info("UrlFilter 동작 => {}", contextPath+path);
                    httpSession.setAttribute("url", path);
                } else {
                    log.info("UrlFilter 동작 => {}", contextPath+path+"?"+query);
                    httpSession.setAttribute("url", path + "?" + query);
                }
                log.info("urlFilter 확인 => {}", httpSession.getAttribute("url"));
                log.info("request 확인하기 => {}", request.getMethod());
            }

            filterChain.doFilter(request, response); // controller 정상 진입
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
