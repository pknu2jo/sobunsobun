package com.example.controller.km;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mapper.km.CustomerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class KmCustomerContorller {
    
    @Autowired CustomerMapper cMapper; 

    @GetMapping(value = "/item/select.do")
    public String selectitemGET() {
        log.info("물품 상세 조회");
        return "/km/customer/selectitem";
    }

    @GetMapping(value = "/kmtest.do")
    public String testGET() {
        log.info("푸터 인클루드 테스트");
        return "/km/customer/headertest";
    }
    
}
