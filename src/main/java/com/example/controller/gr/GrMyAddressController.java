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
import com.example.dto.CustomerAddress;
import com.example.service.gr.GrCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class GrMyAddressController {

    final GrCustomerService cService;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/myaddresschk.do")
    public String myinfochkGET(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        Customer c = cService.searchkakao(user.getUsername());

        // log.info("dkdldle => {}", c.toString());
        if (c != null) {
            return "redirect:/customer/myaddress.do";
        } else {
            return "/gr/customer/myaddresschk";
        }

    }

    @PostMapping(value = "/myaddresschk.do")
    public String mypwchkGET(@AuthenticationPrincipal User user,
            @ModelAttribute Customer customer,
            Model model) {
        model.addAttribute("user", user);
        try {

            Customer c = cService.selectCustomerOne1(user.getUsername());

            if (bcpe.matches(customer.getPw(), c.getPw())) {
                return "redirect:/customer/myaddress.do";
            }
            return "redirect:/customer/myaddresschk.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/myaddresschk.do";
        }
    }

    @GetMapping(value = "/myaddress.do")
    public String myaddressGET(@AuthenticationPrincipal User user, Model model) {
        try {
            model.addAttribute("user", user);
            CustomerAddress c = cService.selectOneCustomerAddress(user.getUsername());
            log.info("rkfka=>{}", c.toString());

            model.addAttribute("c", c);

            return "/gr/customer/myaddress";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }

    }

    @PostMapping(value = "/myaddress.do")
    public String myaddressPOST(@AuthenticationPrincipal User user, @ModelAttribute CustomerAddress customeraddress) {

        CustomerAddress c = cService.selectOneCustomerAddress(user.getUsername());

        c.setPostcode(customeraddress.getPostcode());
        c.setAddress1(customeraddress.getAddress1());
        c.setAddress2(customeraddress.getAddress2());
        c.setAddress3(customeraddress.getAddress3());
        c.setLatitude(customeraddress.getLatitude());
        c.setLongitude(customeraddress.getLongitude());
        c.setMemId(user.getUsername());

        int ret = cService.updateaddress(c);

        log.info("rkfka update=>{}", ret);

        return "redirect:/customer/myaddress.do";

    }

}
