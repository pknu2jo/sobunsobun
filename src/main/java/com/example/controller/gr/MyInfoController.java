package com.example.controller.gr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyInfoController {

    @GetMapping(value = "/myinfochk.do")
    public String myinfochkGET() {
        return "/gr/customer/myinfochk";
    }

}
