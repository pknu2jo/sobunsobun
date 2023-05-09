package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.mapper.CustomerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class CustomerContorller {
    
    @Autowired CustomerMapper cMapper; 

    // 홈화면
    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "/customer/home";
    }

    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/customer/join";
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
            return "redirect:home.do";
        }
        return "redirect:join.do";
    }

    // 로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "/customer/login";
    }

    // 오류 페이지
    @GetMapping(value = "/403page.do")
    public String page403GET() {
        return "/customer/403page";
    }
    
}
