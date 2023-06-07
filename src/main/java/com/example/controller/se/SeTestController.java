package com.example.controller.se;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class SeTestController {

    @GetMapping(value = "/notitest.do")
    public String notiTestGET() {
        try {

            return "/se/customer/notitest";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }
    
}
