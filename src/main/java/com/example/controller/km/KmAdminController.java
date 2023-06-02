package com.example.controller.km;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/admin")
@Slf4j
@RequiredArgsConstructor
public class KmAdminController {
    
    // final HttpSession httpSession; // 정보 전달용 session 객체 생성

    @GetMapping(value = "/product.do")
    public String productGET() {
        try {
            return "/km/admin/product";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.do";
        }
    }
}
