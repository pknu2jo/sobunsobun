package com.example.controller.km;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/main")
@RequiredArgsConstructor
public class KmIndexController {
    
    @GetMapping(value = "")
    public String indexGET(Model model) {
        try {
            return "/km/total/index";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    // 세션 없애고 고객 화면 보내기
    @GetMapping(value = "/logoutAction.do")
    public String logoutAdminGET(HttpServletRequest request, HttpServletResponse response, 
                                @AuthenticationPrincipal User user, @RequestParam(name = "menu") String menu) {
        try {

            log.info("checkcheck => {}", menu);

            if(menu.equals("customer")) {

                if(user != null ){
                    boolean hasCustomerRole =user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("CUSTOMER"));
            
                    if(!hasCustomerRole) {
                        Authentication authenticationToken1 =  SecurityContextHolder.getContext().getAuthentication();
                        log.info("logoutCustomer.do => {}", authenticationToken1.toString());
    
                        if(authenticationToken1 != null) {
                            new SecurityContextLogoutHandler().logout(request, response, authenticationToken1);
                        }
                    }
                }
                
                return "redirect:/customer/home.do";

            } else if(menu.equals("admin")) {

                if(user != null) {
                    boolean hasCustomerRole =user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            
                    if(!hasCustomerRole) {
                        Authentication authenticationToken1 =  SecurityContextHolder.getContext().getAuthentication();
                        log.info("logoutCustomer.do => {}", authenticationToken1.toString());
    
                        if(authenticationToken1 != null) {
                            new SecurityContextLogoutHandler().logout(request, response, authenticationToken1);
                        }
                        return "redirect:/admin/login.do";
                    } else {
                        return "redirect:/admin/home.do";
                    }
                } else {
                    return "redirect:/admin/login.do";
                } 
                
            } else if(menu.equals("seller")) {
                if(user != null) {
                    boolean hasCustomerRole =user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_SELLER"));
            
                    if(!hasCustomerRole) {
                        Authentication authenticationToken1 =  SecurityContextHolder.getContext().getAuthentication();
                        log.info("logoutCustomer.do => {}", authenticationToken1.toString());
    
                        if(authenticationToken1 != null) {
                            new SecurityContextLogoutHandler().logout(request, response, authenticationToken1);
                        }
                        return "redirect:/seller/login.do";
                    } else {
                        return "redirect:/seller/home.do";
                    }
                } else {
                    return "redirect:/seller/login.do";
                }           
            }

            
            return "redirect:/customer/login.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.do";
        }
    }
}
