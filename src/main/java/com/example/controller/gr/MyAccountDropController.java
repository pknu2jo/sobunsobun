package com.example.controller.gr;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.mapper.gr.GrCustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyAccountDropController {

    final GrCustomerMapper cMapper;

    @GetMapping(value = "myaccountdropchk.do")
    public String myaccountdropchkGET() {
        return "/gr/customer/myaccountdropchk";
    }

    @GetMapping(value = "myaccountdrop.do")
    public String myaccountdropGET(@AuthenticationPrincipal User user,
            Model model) {
        model.addAttribute("user", user);
        return "/gr/customer/myaccountdrop";
    }

    @PostMapping(value = "myaccountdrop.do")
    public String myaccountdropPOST(@ModelAttribute Customer customer) {
        try {
            customer.setId("garam2");
            cMapper.myaccountdrop(customer);
            log.info("rkfka update => {}", customer.toString());
            return "redirect:/customer/home.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/myaccountdrop.do";
        }
    }

}
