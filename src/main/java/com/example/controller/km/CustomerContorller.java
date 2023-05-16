package com.example.controller.km;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Customer;
import com.example.mapper.km.CustomerMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class CustomerContorller {
    
    @Autowired CustomerMapper cMapper; 


    @GetMapping(value = "selectitem.do")
    public String selectitem() {
        return "/se/selectitem";
    }
    
}
