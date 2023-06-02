package com.example.controller.gr;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminLoginController {

    @GetMapping(value = "/login.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        try {
            model.addAttribute("user", user);
            return "/gr/admin/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/login.do";
        }
    }

}
