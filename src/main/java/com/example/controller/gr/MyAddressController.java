package com.example.controller.gr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.CustomerAddress;
import com.example.mapper.gr.GrCustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyAddressController {

    final GrCustomerMapper cMapper;

    @GetMapping(value = "/myaddresschk.do")
    public String myaddresschkGET() {
        return "/gr/customer/myaddresschk";
    }

    @GetMapping(value = "/myaddress.do")
    public String myaddressGET(Model model) {
        try {
            CustomerAddress c = cMapper.selectOneCustomerAddress("3");
            log.info("rkfka=>{}", c.toString());

            model.addAttribute("c", c);

            return "/gr/customer/myaddress";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }

    }

    @PostMapping(value = "/myaddress.do")
    public String myaddressPOST(@ModelAttribute CustomerAddress customeraddress) {

        CustomerAddress c = cMapper.selectOneCustomerAddress("3");

        c.setPostcode(customeraddress.getPostcode());
        c.setAddress1(customeraddress.getAddress1());
        c.setAddress2(customeraddress.getAddress2());
        c.setAddress3(customeraddress.getAddress3());
        c.setLatitude(customeraddress.getLatitude());
        c.setLongitude(customeraddress.getLongitude());
        c.setMemId("3");

        int ret = cMapper.updateaddress(c);

        log.info("rkfka update=>{}", ret);

        return "redirect:/customer/myaddress.do";

    }

}
