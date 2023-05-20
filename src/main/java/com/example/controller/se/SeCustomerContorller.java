package com.example.controller.se;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.CustomerAddressEntity;
import com.example.entity.CustomerEntity;
import com.example.service.se.SeCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class SeCustomerContorller {

    final SeCustomerService cService;
    final HttpSession httpSession; // 정보 전달용 session 객체 생성
    
    // ----------------------------------------------------------------------------------------------------
    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET(                                                 
    ) {
        return "/se/customer/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST( 
        @ModelAttribute CustomerEntity customer,
        @ModelAttribute CustomerAddressEntity customerAddress
        ) {
        try {
            log.info("회원가입 => {}", customer.toString());
            log.info("회원가입 => {}", customerAddress.toString());
            
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            customer.setPw( bcpe.encode(customer.getPw()) );
            log.info("회원가입 => {}", customerAddress.getCustomer());
            customerAddress.setCustomer(customer);
            // customerAddress.getCustomer().setId(customer.getId()); // 왜 안되지..?
            cService.joinCustomerOne(customer, customerAddress);
    
            return "redirect:/customer/login.do";
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

    // ----------------------------------------------------------------------------------------------------
    // 아이디찾기
    @GetMapping(value="/findid.do")
    public String findidGET() {
        return "/se/customer/findid";
    }
    
    @PostMapping(value="/findid.do")
    public String findidPOST( @ModelAttribute CustomerEntity customer ) {
        try {
            log.info("아이디찾기 => {}", customer.toString());
            CustomerEntity obj = cService.findId(customer);
            log.info("아이디찾기2 => {}", obj.toString());
            httpSession.setAttribute("name", obj.getName());
            httpSession.setAttribute("id", obj.getId());
            return "redirect:/customer/findidok.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findid.do";
        }
    }

    // 아이디찾기 - 결과화면
    @GetMapping(value="/findidok.do")
    public String findidokGET(
        Model model
    ) {
        try {
            model.addAttribute("name", httpSession.getAttribute("name"));
            model.addAttribute("id", httpSession.getAttribute("id"));
            return "/se/customer/findidok";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findid.do";
        }
    }
    
    // ----------------------------------------------------------------------------------------------------
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
