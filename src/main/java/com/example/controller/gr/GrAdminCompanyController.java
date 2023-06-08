package com.example.controller.gr;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.SellerEntity;
import com.example.service.gr.GrAdminCompanyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class GrAdminCompanyController {

    final GrAdminCompanyService acService;

    @GetMapping(value = "/company.do")
    public String companyGET(@AuthenticationPrincipal User user, Model model) {
        try {

            List<SellerEntity> list = acService.findAll();

            model.addAttribute("user", user);
            model.addAttribute("list", list);

            log.info("가가가가=>{}", user.getUsername().toString());

            log.info("가람 => {}", list.toString());

            return "/gr/admin/company";
        } catch (Exception e) {
            e.printStackTrace();
            return "/gr/admin/home";
        }

    }

}
