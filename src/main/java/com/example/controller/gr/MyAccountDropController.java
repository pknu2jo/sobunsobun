package com.example.controller.gr;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

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
import com.example.mapper.gr.GrCustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyAccountDropController {

    final GrCustomerMapper cMapper;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/myaccountdropchk.do")
    public String myinfochkGET(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        Customer c = cMapper.searchkakao(user.getUsername());
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

            Customer c = cMapper.selectCustomerOne1(user.getUsername());

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
        int ret = cMapper.countPurchase(user.getUsername());
        model.addAttribute("ret", ret);
        model.addAttribute("user", user);
        return "/gr/customer/myaccountdrop";
    }

    // rest사용했기때문에 주석처리함
    // @PostMapping(value = "/myaccountdrop.do")
    // public String myaccountdropPOST(@AuthenticationPrincipal User user,
    // @ModelAttribute Customer customer) {
    // try {
    // log.info("krkrkr=>{}", user.toString());
    // customer.setId(user.getUsername());
    // CustomerAddress ca = cMapper.selectOneCustomerAddress(user.getUsername());
    // log.info("krkrkrkr => {}", ca.toString());

    // // cMapper.myaccountdrop(customer);
    // cMapper.deletemyaddress(ca);
    // log.info("rkfka update => {}", customer.toString());
    // return "redirect:/customer/home.do";

    // } catch (Exception e) {
    // e.printStackTrace();
    // return "redirect:/customer/myaccountdrop.do";
    // }
    // }

}
