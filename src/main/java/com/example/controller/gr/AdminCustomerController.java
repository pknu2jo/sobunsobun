package com.example.controller.gr;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.CustomerEntity;
import com.example.repository.gr.grcustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminCustomerController {

    final grcustomerRepository gcRepository;

    @GetMapping(value = "/customer.do")
    public String customerGET(@AuthenticationPrincipal User user, Model model) {

        List<CustomerEntity> list = gcRepository.findAll();
        log.info("고객 리스트 => {}", list.toString());
        model.addAttribute("user", user);
        model.addAttribute("list", list);

        return "/gr/admin/customer";
    }

}
