package com.example.restController.se;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerUser;
import com.example.entity.CustomerEntity;
import com.example.service.se.SeCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class SeRestCustomerController {

    final SeCustomerService cService;
    
    // 카카오 로그인
    @PostMapping(value = "/ckakaologin.json")
    public Map<String, Integer> kakaologinPOST(
        @RequestBody Map<String, Object> map
        ) {
        Map<String, Integer> retMap = new HashMap<>(); // 반환용 객체 생성
        try {
            // 넘어온 정보 받기
            // log.info("카카오로그인 => {}", map.get("id"));

            // DB 에 저장된 아이디 중에 일치하는 값이 있으면 => 시큐리티 세션에 로그인한 후 ret:1 반환
            CustomerEntity obj = cService.findById((String)map.get("id"));
            if(obj != null && obj.getQuitchk() == BigDecimal.valueOf(0)){
                log.info("카카오로그인 => {}", obj.toString());

                // 시큐리티 로그인 ---------------------------------------------------------------------------------
                // 세션에 저장할 객체 생성 (UsernamePasswordAuthenticationToken(저장할 객체, null, 권한))
                String[] strRole = {"CUSTOMER"};
                Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);
                obj.setPw(""); // pw => null 이라 오류나서 추가
                User user = new CustomerUser( obj.getId(), obj.getPw(), role, obj.getNickname() ); // import org.springframework.security.core.userdetails.User;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, role);

                // 수동으로 세션에 저장(로그인)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
                // 시큐리티 로그인

                retMap.put("ret", 1);
            }
            // 없으면 ret:0 반환
            else if(obj == null || obj.getQuitchk() == BigDecimal.valueOf(1)){
                log.info("카카오로그인 => {널이다 널}");
                retMap.put("ret", 0);
            }

            // 일단 진행 시켜

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("ret", -1);
        }
        return retMap;
    }

}
