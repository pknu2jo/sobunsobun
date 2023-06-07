package com.example.controller.gr;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.gr.GrAdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class GrAdminhomeController {

    final GrAdminService aService;

    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {

        log.info("home user check => {}", user.toString());

        int countjoin = aService.countTodayCustomers();
        String formattedRet;
        if (countjoin < 10) {
            formattedRet = "0" + countjoin;
        } else {
            formattedRet = String.valueOf(countjoin);
        }

        int countdrop = aService.countByQuitchk();
        String formattedRet1;
        if (countdrop < 10) {
            formattedRet1 = "0" + countdrop;
        } else {
            formattedRet1 = String.valueOf(countdrop);
        }

        int countpurchase = aService.countByPurchase();
        String formattedRet2;
        if (countpurchase < 10) {
            formattedRet2 = "0" + countjoin;
        } else {
            formattedRet2 = String.valueOf(countpurchase);
        }

        model.addAttribute("countjoin", formattedRet);
        model.addAttribute("countdrop", formattedRet1);
        model.addAttribute("countpurchase", formattedRet2);
        model.addAttribute("user", user);
        return "/gr/admin/home";
    }

}
