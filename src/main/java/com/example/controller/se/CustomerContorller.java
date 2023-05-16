package com.example.controller.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.mapper.km.KmCustomerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class CustomerContorller {
    
    @Autowired KmCustomerMapper cMapper; 

    // 홈화면
    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "/se/home";
    }

    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/se/join";
    }
    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Customer customer) {
        log.info("Customer Join => {}", customer);

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        customer.setPw(bcpe.encode(customer.getPw()));
        System.out.println("ret1");
        int ret = cMapper.joinCustomer(customer);
        System.out.println("ret2");
        if( ret == 1 ) {
            return "redirect:/customer/home.do";
        }
        return "redirect:/se/join.do";
    }

    // 로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "/se/login";
    }

    // 오류 페이지
    @GetMapping(value = "/403page.do")
    public String page403GET() {
        return "/error/403page";
    }
    
}
