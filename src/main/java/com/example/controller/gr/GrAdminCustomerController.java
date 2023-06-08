package com.example.controller.gr;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.CustomerEntity;
import com.example.service.gr.GrAdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class GrAdminCustomerController {

    final GrAdminService aService;

    @GetMapping(value = "/customer.do")
    public String customerGET(@AuthenticationPrincipal User user, Model model) {

        try {

            List<CustomerEntity> list = aService.findAll();
            log.info("고객 리스트 => {}", list.toString());
            model.addAttribute("user", user);
            model.addAttribute("list", list);

            return "/gr/admin/customer";
        } catch (Exception e) {
            e.printStackTrace();
            return "/gr/admin/home";
        }

    }

}
