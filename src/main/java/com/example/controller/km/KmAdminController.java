package com.example.controller.km;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.StorageEntity;
import com.example.service.km.KmAdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/admin")
@Slf4j
@RequiredArgsConstructor
public class KmAdminController {
    
    // final HttpSession httpSession; // 정보 전달용 session 객체 생성
    final KmAdminService adminService;

    @GetMapping(value = "/product.do")
    public String productGET(Model model) {
        try {

            List<StorageEntity> storageList =  adminService.findAllStorage();

            // log.info("storage List check => {}", storageList.toString());
            model.addAttribute("storageList", storageList);

            return "/km/admin/product";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.do";
        }
    }

    @GetMapping(value = "/logoutAdmin.do")
    public String logoutAdminGET(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authenticationToken1 =  SecurityContextHolder.getContext().getAuthentication();
            if(authenticationToken1 != null) {
                new SecurityContextLogoutHandler().logout(request, response, authenticationToken1);
            }
           
            return "redirect:/customer/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.do";
        }
    }


}
