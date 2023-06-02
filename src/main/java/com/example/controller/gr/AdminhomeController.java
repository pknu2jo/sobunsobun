package com.example.controller.gr;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminhomeController {

    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user) {

        log.info("home user check => {}", user.toString());
        System.out.println("가나다라마바사");
        return "/gr/admin/home";
    }

}
