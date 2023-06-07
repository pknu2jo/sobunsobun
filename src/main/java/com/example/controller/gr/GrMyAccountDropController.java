package com.example.controller.gr;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.service.gr.GrCustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class GrMyAccountDropController {

    final GrCustomerService cService;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/myaccountdropchk.do")
    public String myinfochkGET(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        Customer c = cService.searchkakao(user.getUsername());
        if (c != null) {
            return "redirect:/customer/myaccountdrop.do";
        } else {
            return "/gr/customer/myaccountdropchk";
        }

    }

    @PostMapping(value = "/myaccountdropchk.do")
    public String mypwchkGET(@AuthenticationPrincipal User user,
            @ModelAttribute Customer customer,
            Model model) {
        model.addAttribute("user", user);
        try {

            Customer c = cService.selectCustomerOne1(user.getUsername());

            if (bcpe.matches(customer.getPw(), c.getPw())) {
                return "redirect:/customer/myaccountdrop.do";
            }
            return "redirect:/customer/myaccountdropchk.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/myaccountdropchk.do";
        }
    }

    @GetMapping(value = "/myaccountdrop.do")
    public String myaccountdropGET(@AuthenticationPrincipal User user,
            Model model) {
        int ret = cService.countPurchase(user.getUsername());
        model.addAttribute("ret", ret);
        model.addAttribute("user", user);
        return "/gr/customer/myaccountdrop";
    }

}
