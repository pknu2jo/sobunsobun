package com.example.controller.jk;

import javax.servlet.http.HttpSession;

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

    final HttpSession httpSession;
    final JkSellerService sService; // Mybatis 방식 Service (Mapper)
    final JkSellerRepository sRepository; // Jpa 방식 Repository
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    /* ------------------------------- 홈화면 --------------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/home.do
    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        log.info("Seller home User => {}", user);
        return "/jk/seller/home";
    }
    /* --------------------------- 회원가입 (Mybatis) ----------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/join.do
    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/jk/seller/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Seller seller,
            @RequestParam(name = "address1", defaultValue = "") String address1, // address1은 우편번호로, DB에 저장될때는 제외시켰음.
            @RequestParam(name = "address2", defaultValue = "") String address2,
            @RequestParam(name = "address3", defaultValue = "") String address3,
            @RequestParam(name = "address4", defaultValue = "") String address4) {

        log.info("{}", seller.toString());
        log.info("Seller address => {}", address1 + address2 + address3 + address4);
        seller.setAddress(address2 + " " + address3 + " " + address4); // 우편번호는 제외
        seller.setPw(bcpe.encode(seller.getPw()));
        int ret = sService.joinSeller(seller);

        if (ret == 1) {
            return "/jk/seller/login";
        }
        return "redirect:/seller/join.do";
    }
    /* -------------------------------- 로그인 (Jpa) -------------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/login.do
    // loginaction.do post는 만들지 않고 sercurity에서 처리.
    @GetMapping(value = "/login.do")
    public String loginGET() {
        try {
            return "jk/seller/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
            
        }
    }

    // @PostMapping(value = "/loginaction.do")
    // public String loginPOST(@ModelAttribute com.example.entity.Seller seller) {
    //     try {

    //         log.info("sellerLogin => {}", seller.toString()); // view에서 잘전송되었는지

    //         // // DetailsService를 사용하지 않고 세션에 저장하는 방식
    //         // // 1. 사업자번호로 데이터 읽기
    //         // com.example.entity.Seller obj = sRepository.findById(seller.getNo()).orElse(null); // 로그인한 사용자의 정보 반환

    //         // // 2. DB암호 vs 입력된 암호 [일치대조]
    //         // if (bcpe.matches(seller.getPw(), obj.getPw())) {
                
    //         //     httpSession.setAttribute("USERID", obj.getNo());
    //         //     httpSession.setAttribute("USEPASSWORD", obj.getPw());
    //         //     return "redirect:/home.do"; // 로그인 성공 시
    //         // }
    //         return "redirect:/home.do"; // 로그인 성공 시
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/login.do"; // 로그인 실패 시
    //     }

    // }

    /* ----------------------------- 마이페이지 ---------------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/updateinfo.do
    // 1. 업체정보
    // 여기서부터 드래그 & 주석해제
    // @GetMapping(value = "/updateinfo.do")
    // public String updateInfoGET() {
    // return "jk/seller/mypage/updateinfo";
    // }

    // @PostMapping(value = "/updateinfo.do")
    // public String updateInfoPOST() {
    // return "";
    // }

    // // http://127.0.0.1:5959/SOBUN/seller/updatepw.do
    // // 2. 비번변경
    // @GetMapping(value = "/updatepw.do")
    // public String updatePwGET() {
    // return "jk/seller/mypage/updatepw";
    // }

    // @PostMapping(value = "/updateinfo.do")
    // public String updatePwPOST() {
    // return "";
    // }

    // // http://127.0.0.1:5959/SOBUN/seller/unregister.do
    // // 3. 탈퇴
    // @GetMapping(value = "/unregister.do")
    // public String unRegisterGET() {
    // return "jk/seller/mypage/unregister";
    // }

    // @PostMapping(value = "/updateinfo.do")
    // public String unRegisterPOST(@ModelAttribute Seller seller) {
    // sRepository.deleteById(seller.getNo());
    // return "";
    // }

    /* ------------------------------------------------------------------------ */
}
