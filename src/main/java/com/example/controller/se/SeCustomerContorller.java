package com.example.controller.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Customer;
import com.example.repository.se.SeCustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class SeCustomerContorller {

    @Autowired SeCustomerRepository cRepository;
    
    // ----------------------------------------------------------------------------------------------------
    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET(                                                 
    ) {
        return "/se/customer/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST( @ModelAttribute Customer customer ) {
        try {
            log.info("회원가입 => {}", customer.toString());
            
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            customer.setPw( bcpe.encode(customer.getPw()) );
            cRepository.save(customer);
    
            return "redirect:/customer/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }

    // ----------------------------------------------------------------------------------------------------
    // 로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "/se/customer/login";
    }

    // 홈화면
    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "/se/customer/home";
    }





    // 오류 페이지
    @GetMapping(value = "/403page.do")
    public String page403GET() {
        return "/error/403page";
    }
    
}
