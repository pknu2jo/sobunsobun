package com.example.controller.km;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mapper.km.KmCustomerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class KmCustomerContorller {
    
    @Autowired KmCustomerMapper cMapper; 

    @GetMapping(value = "/item/select.do")
    public String selectitemGET(Model model) {
        // @RequestParam(name = "no") long no 로 itemno 받기
        log.info("물품 상세 조회 GET");

        



        return "/km/customer/selectitem";
    }


    @GetMapping(value = "/item/order.do")
    public String orderGET() {
        log.info("결제하기 GET");

        return "/km/customer/checkout";
    }







    @GetMapping(value = "/kmtest.do")
    public String testGET(Model model) {
        log.info("푸터 인클루드 테스트");

        int person = cMapper.countRemainingPerson(1004);
        log.info("person => {}", person);
        //model.addAttribute("person", person);
        return "/km/customer/sample";
    }
    
}
