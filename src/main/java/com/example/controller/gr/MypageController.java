package com.example.controller.gr;

import java.lang.ProcessHandle.Info;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.dto.CustomerUser;
import com.example.mapper.gr.GrCustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MypageController {

    final GrCustomerMapper cMapper;

    @GetMapping(value = "/mypage.do")
    public String mypageGET(Model model) {

        int ret = cMapper.countPurchase("test2");
        log.info("retretret => {}", ret);
        model.addAttribute("ret", ret);

        return "/gr/customer/mypage";
    }

    // @PostMapping(value = "/mypage.do")
    // public String mypagePOST(
    // @AuthenticationPrincipal CustomerUser user,
    // @ModelAttribute Customer customer,
    // Model model) {

    // model.addAttribute("user", user);

    // // model.addAttribute("ret", ret);

    // return "redirect:/customer/mypage.do";

    // }

}
