package com.example.controller.jk;

import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.Seller;
import com.example.entity.SellerEntity;
import com.example.repository.jk.JkSellerRepository;
import com.example.service.jk.JkSellerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/seller")
@Slf4j
@RequiredArgsConstructor
public class JkSellerController {

    final JkMailController mailController;
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
    /*
     * -------------------------------- 로그인 (Jpa) --------------------------------
     */

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

    // GET, POST가 같은 동작을 함.
    // 로그아웃
    @RequestMapping (value="/logout.do", method={RequestMethod.GET, RequestMethod.POST})
    public String logoutPOST() {
        httpSession.invalidate(); // 세션의 정보를 모두 지움.
        return "redirect:/home.do";
    }

    // 비밀번호 찾기
    // http://127.0.0.1:5959/SOBUN/seller/findpw.do
    @GetMapping(value = "/findpw.do")
    public String findpwGET() {
        return "jk/seller/findpw";
    }

    @PostMapping(value = "/findpw.do")
    public String findpwPOST(@ModelAttribute Seller seller) {
        // 자동으로 임시 비밀번호를 생성, 설정한 뒤 메일에 첨부하는 방식.
        log.info("Find password 1 => {}", seller.toString());
        String tempPw = UUID.randomUUID().toString().replace("-", ""); // 난수 생성시에 '-'는 제외함.
        tempPw = tempPw.substring(0, 10); // 10자리수의 새로운 암호 발급
        // (임시비밀번호는 이메일에서 복사 후 붙여넣는 용도라 자릿수는 크게 신경쓰지 않았음.)

        seller.setNewPw(bcpe.encode(tempPw)); // 암호화 과정
        int ret = sService.findSellerPw(seller); // 필요한 정보 : no, newPw, email
        log.info("Find password 2 => {}", seller.toString());
        if (ret == 1) {
            mailController.sendPwMail(seller.getEmail(), tempPw);
            return "/jk/seller/login";
        }
        return "redirect:/seller/findpw.do";
    }

    /* ----------------------------- 마이페이지(Jpa) ---------------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/updateinfo.do
    // 1. 업체정보
    @GetMapping(value = "/updateinfo.do")
    public String updateInfoGET() { // 기존 업체의 정보를 받아와야함. (업체명, 주소, 연락처 이메일) 
        return"jk/seller/mypage/updateinfo";
    }

    @PostMapping(value = "/updateinfo.do")
    public String updateInfoPOST(@AuthenticationPrincipal User user, @ModelAttribute SellerEntity seller1) {
        try {
            log.info("mypage info => {}");
            // 기존 정보 가져오기
            String sellerId = user.getUsername();
            SellerEntity seller2 = sRepository.findById(sellerId).orElse(null);
            // 변경항목 수정
            seller2.setName(seller1.getName());
            seller2.setAddress(seller1.getAddress());
            seller2.setPhone(seller1.getPhone());
            seller2.setEmail(seller1.getEmail());
            sRepository.save(seller2);
            return "redirect:/updateinfo.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // http://127.0.0.1:5959/SOBUN/seller/updatepw.do
    // 2. 비번변경
    @GetMapping(value = "/updatepw.do")
    public String updatePwGET() {
    return "jk/seller/mypage/updatepw";
    }

    // @PostMapping(value = "/updateinfo.do")
    // public String updatePwPOST() {
    // return "";
    // }

    // http://127.0.0.1:5959/SOBUN/seller/unregister.do
    // 3. 탈퇴
    @GetMapping(value = "/unregister.do")
    public String unRegisterGET() {
        return "jk/seller/mypage/unregister";
    }

    // @PostMapping(value = "/unregister.do")
    // public String unRegisterPOST(@ModelAttribute Seller seller) {
    // log.info("Seller unRegister => {}", seller.toString());
    // sRepository.deleteById(seller.getNo());
    // if(seller.getNo() == null){
    // return "return:/login.do"; // 성공시 로그인페이지로.
    // }

    // }

    /* ------------------------------------------------------------------------ */
}
