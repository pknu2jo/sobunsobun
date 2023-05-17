package com.example.controller.jk;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Seller;
import com.example.repository.jk.JkSellerRepository;
import com.example.service.jk.JkSellerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/seller")
@Slf4j
@RequiredArgsConstructor
public class JkSellerController {

    final JkSellerService sService; // Mybatis 방식 Service (Mapper)
    final JkSellerRepository sRepository; // Jpa 방식 Repository
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    /* -------------------------- 홈화면 ---------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/home.do
    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        try {
            log.info("Seller home User => {}", user);
            return "/seller/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "/seller/home";
        }

    }
    /* --------------------------- 회원가입 (Mybatis) ----------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/join.do
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/jk/seller/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Seller seller,
            @RequestParam(name = "address1", defaultValue = "") String address1,
            @RequestParam(name = "address2", defaultValue = "") String address2,
            @RequestParam(name = "address3", defaultValue = "") String address3,
            @RequestParam(name = "address4", defaultValue = "") String address4) {

        log.info("{}", seller.toString());
        log.info("Seller address => {}", address1 + address2 + address3 + address4);
        seller.setAddress(address1 + " " + address2 + " " + address3 + " " + address4);
        seller.setPw(bcpe.encode(seller.getPw()));
        int ret = sService.joinSeller(seller);

        if (ret == 1) {
            return "/jk/seller/login";
        }
        return "redirect:/seller/join.do";
    }
    /* --------------------------- 로그인 --------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/login.do
    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "jk/seller/login";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@ModelAttribute Seller seller) {

    seller.setNo(no);
    seller.setPw(pw);
    Seller ret = sService.sellerLogin(seller);
    log.info("Seller login => {}", ret.toString());

    if (ret.getNo() == null) {
    return "";
    }
    return "redirect:/seller/login.do";
    }

    /* ------------------------ 마이페이지 () ----------------------------- */

    //
    // 1. 업체정보

    // 2. 비번변경

    // 3. 탈퇴

    /* -------------------------------------------------------------- */
}
