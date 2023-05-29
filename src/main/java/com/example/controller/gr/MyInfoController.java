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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Customer;
import com.example.mapper.gr.GrCustomerMapper;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyInfoController {

    final GrCustomerMapper cMapper;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/myinfochk.do")
    public String myinfochkGET(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        Customer c = cMapper.searchkakao(user.getUsername());

        // log.info("dkdldle => {}", c.toString());
        if (c != null) {
            return "redirect:/customer/myinfo.do";
        } else {
            return "/gr/customer/myinfochk";
        }
    }

    @PostMapping(value = "/myinfochk.do")
    public String mypwchkGET(@AuthenticationPrincipal User user,
            @ModelAttribute Customer customer,
            Model model) {
        model.addAttribute("user", user);
        try {

            Customer c = cMapper.selectCustomerOne1(user.getUsername());

            if (bcpe.matches(customer.getPw(), c.getPw())) {
                return "redirect:/customer/myinfo.do";
            }
            return "redirect:/customer/myinfochk.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/myinfochk.do";
        }
    }

    @GetMapping(value = "/myinfo.do")
    public String myinfoGET(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("user", user);

        Customer c = cMapper.selectCustomerOne1(user.getUsername());
        log.info("rkfka => {}", c);

        String[] obj = c.getEmail().split("@");
        for (int i = 0; i < obj.length; i++) {
            String email = obj[i];
            log.info("rkfka=>{}", email);
        }
        model.addAttribute("c", c);
        model.addAttribute("id", obj[0]);
        model.addAttribute("email", obj[1]);

        return "/gr/customer/myinfo";
    }

    @PostMapping(value = "/myinfo.do")
    public String myinfoPOST(@ModelAttribute Customer customer,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "email1") String email1,
            @RequestParam(name = "email2") String email2) {

        Customer c = cMapper.selectCustomerOne1(user.getUsername());

        c.setName(customer.getName());
        c.setNickname(customer.getNickname());
        c.setPhone(customer.getPhone());
        String result = email1.concat("@").concat(email2);
        c.setEmail(result);

        int ret = cMapper.updateinfo(c);

        log.info("rkfka result=>{}", result);
        log.info("rkfka update=>{}", ret);

        return "redirect:/customer/myinfo.do";
    }

    @PostMapping(value = "/updatepw.do")
    public String updatepwPOST(@ModelAttribute Customer customer,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "pw") String pw,
            @RequestParam(name = "pwConfirm") String pwConfirm) {

        log.info("rkfka lsd123");

        System.out.println("123456789 " + bcpe.encode(customer.getPw()));

        Customer c = cMapper.selectCustomerOne1(user.getUsername());

        c.setId(user.getUsername());
        c.setNewPw(bcpe.encode(customer.getPw()));
        cMapper.updatepw(c);
        log.info("rkfka 비번변경 => {}", customer.toString());

        return "redirect:/customer/myinfo.do";

    }
}