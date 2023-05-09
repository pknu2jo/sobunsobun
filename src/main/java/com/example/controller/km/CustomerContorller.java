package com.example.controller.km;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.mapper.km.CustomerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class CustomerContorller {
    
    @Autowired CustomerMapper cMapper; 

    // 회원 정보 불러오기
    @GetMapping(value = "/select.do")
    public String selectGET(Model model) {
        Customer obj = cMapper.selectCustomerOne("2");

        log.info("customerController => {}", obj.toString());
        model.addAttribute("obj", obj);
        return "/km/customer/select";
    }

    // 홈화면
    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "/km/customer/home";
    }

    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/km/customer/join";
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
            return "redirect:/km/customer/home.do";
        }
        return "redirect:/km/customer/join.do";
    }

    // 로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "/km/customer/login";
    }

    // 오류 페이지
    @GetMapping(value = "/403page.do")
    public String page403GET() {
        return "/km/customer/403page";
    }
    
}
