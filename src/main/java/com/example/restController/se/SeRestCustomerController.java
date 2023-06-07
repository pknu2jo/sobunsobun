package com.example.restController.se;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerUser;
import com.example.entity.CNotificationEntity;
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

    // -------------------------------------------------------------------------------------------------------------------------------
    // 탈퇴한 회원, 블랙리스트, 없는 회원 확인
    @GetMapping(value = "/chkcustomerstate.json")
    public Map<String, Object> selectstateGET(
        @RequestParam(name = "id") String id
    ) {
        Map<String, Object> retMap = new HashMap<>();
        try {

            int ret = 0; // 정상회원 ret = 0

            CustomerEntity customerEntity = cService.findById(id);
            
            // 1) 없는 회원 => ret = 1
            if(customerEntity == null) {
                ret = 1;
            }
            
            // 2) 탈퇴한 회원 => ret = 2
            else if(customerEntity.getQuitchk() == BigDecimal.valueOf(1L)) {
                ret = 2;
            }

            // 3) 블랙리스트 => ret = 3
            else if(customerEntity.getBlockchk() == BigDecimal.valueOf(1L)) {
                ret = 3;
            }



            retMap.put("ret", ret);
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("ret", -1);
        }
        return retMap;
    }

    // -------------------------------------------------------------------------------------------------------------------------------
    // 카카오 로그인
    @PostMapping(value = "/ckakaologin.json")
    public Map<String, Integer> kakaologinPOST(
        @RequestBody Map<String, Object> map
        ) {
        Map<String, Integer> retMap = new HashMap<>(); // 반환용 객체 생성
        try {
            // 넘어온 정보 받기
            // log.info("카카오로그인 => {}", map.get("id"));

            // DB 에 저장된 아이디 중에 일치하는 값이 있으면 => 시큐리티 세션에 로그인
            CustomerEntity obj = cService.findById((String)map.get("id"));

            // 등록된 회원이 없을 때 회원가입으로 ret = 0
            // 등록된 회원이 탈퇴했을 때 ret = 1
            // 등록된 회원이 블랙리스트일 때 ret = 2
            // 정상 로그인 ret = 3 => 바로 홈화면 연결
            if(obj == null) {
                log.info("카카오로그인 => {null}");
                retMap.put("ret", 0);
            }
            else if(obj.getQuitchk() == BigDecimal.valueOf(1L)) {
                log.info("카카오로그인 => {탈퇴회원}");
                retMap.put("ret", 1);
            }
            else if(obj.getBlockchk() == BigDecimal.valueOf(1L)) {
                log.info("카카오로그인 => {블랙리스트회원}");
                retMap.put("ret", 2);
            }
            else {
                log.info("카카오로그인 => {정상회원}");

                // 시큐리티 로그인 ---------------------------------------------------------------------------------
                // 세션에 저장할 객체 생성 (UsernamePasswordAuthenticationToken(저장할 객체, null, 권한))
                String[] strRole = {"CUSTOMER"};
                Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);
                obj.setPw(""); // pw => null 이라 오류나서 추가
                User user = new CustomerUser( obj.getId(), obj.getPw(), role, obj.getNickname() ); // import org.springframework.security.core.userdetails.User;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, role);
                log.info("카카오로그인 user => {}", user.toString());

                // 수동으로 세션에 저장(로그인)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
                // 시큐리티 로그인

                retMap.put("ret", 3);
            }


        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("ret", -1);
        }
        return retMap;
    }


    // -------------------------------------------------------------------------------------------------------------------------------
    // 알림 가져오기
    @GetMapping(value = "/selectnoti.json")
    public Map<String, Object> selectnotiGET(
        @RequestParam(name = "id") String id
    ) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate oneMonthAgo = currentDate.minusMonths(1);
            Date oneMonthAgoDate = Date.from(oneMonthAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
            // log.info("여기좀보세요 => {}", oneMonthAgoDate);

            List<CNotificationEntity> list = cService.findByCustomerEntity_idAndRegdateAfter(id, oneMonthAgoDate);
            // log.info("여기좀보세요 => {}", list1);

            retMap.put("list", list);
            retMap.put("ret", 1);
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("ret", -1);
        }
        return retMap;
    }
}
