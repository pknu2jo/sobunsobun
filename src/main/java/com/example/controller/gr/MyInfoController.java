package com.example.controller.gr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Customer;
import com.example.mapper.gr.GrCustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyInfoController {

    final GrCustomerMapper cMapper;

    @GetMapping(value = "/myinfochk.do")
    public String myinfochkGET() {
        return "/gr/customer/myinfochk";
    }

    @GetMapping(value = "/myinfo.do")
    public String myinfoGET(Model model) {

        Customer c = cMapper.selectCustomerOne1("5");
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
            @RequestParam(name = "email1") String email1,
            @RequestParam(name = "email2") String email2) {

        Customer c = cMapper.selectCustomerOne1("5");

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

}
