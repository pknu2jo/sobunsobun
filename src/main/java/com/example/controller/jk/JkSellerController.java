package com.example.controller.jk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Seller;
import com.example.mapper.jk.SellerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/seller")
@Slf4j
public class SellerController {
    @Autowired SellerMapper sMapper; 
    

    /* -------------------------------------------------------------- */
    // 홈화면
    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "/seller/home";
    }
    /* -------------------------------------------------------------- */

    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/seller/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Seller seller) {
        log.info("Seller join => {}", seller );

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        seller.setPw(bcpe.encode(seller.getPw()));
        int ret = sMapper.joinSeller(seller);
        if( ret == 1) {
            return "/seller/index.do";
        }
        return "redirect:join.do";
    }
    /* -------------------------------------------------------------- */

    // 정보수정
    //  1. 업체정보




    // 2. 비번변경




    // 3. 탈퇴
    

    /* -------------------------------------------------------------- */
}
