package com.example.controller.gr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Purchase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminhomeController {

    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "/gr/admin/home";
    }

}
