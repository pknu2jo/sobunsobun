package com.example.controller.jk;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dto.Seller;
import com.example.entity.SellerEntity;
import com.example.entity.ikh.BestSellView;
import com.example.entity.ikh.TopthreeView;
import com.example.repository.ikh.BestSellViewRepository;
import com.example.repository.ikh.TopthreeViewRepository;
import com.example.repository.jk.JkSellerRepository;
import com.example.service.jk.JkSellerService;
import com.example.service.mj.MjItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/seller")
@Slf4j
@RequiredArgsConstructor
public class JkSellerController {

    final JkMailController mailController;
    final HttpSession httpSession;
    final JkSellerService sSellerService; // Mybatis 방식 Service (Mapper)
    final JkSellerRepository sRepository; // Jpa 방식 Repository
    final MjItemService itemService;
    final BestSellViewRepository bsvRepository;
    final TopthreeViewRepository ttvRepository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    /* ------------------------------- 홈화면 --------------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/home.do
    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) {
        // log.info("판매자 Home 정보 받아오기 => {}", user);
        if (!user.getUsername().equals("_")) {
            SellerEntity seller = sSellerService.findByNo(user.getUsername());
            // log.info("확인해봅시다 => {}", seller.toString());
            model.addAttribute("companyName", seller.getName().toString());

            long ret = itemService.countItems(seller.getNo());
            model.addAttribute("countItem", ret);

           // 지금까지 가장 많이 팔린 상품은 파트
           BestSellView best = bsvRepository.findByNo(seller.getNo());
           if(best != null ) { 
               log.info("확인해봅시다 => {}", best.toString());
               model.addAttribute("itemName", best.getItemname()); // 상품명
               model.addAttribute("soldCount", best.getCount()); // 팔린 개수
           }

           // 가장 많이 팔린 상품들이에요! 파트
           List<TopthreeView> list = ttvRepository.findByNo(seller.getNo());
           if(list != null){
               log.info("topthree => {}", list.toString());
               model.addAttribute("list", list);
           }

            return "/jk/seller/home";
        } else {
            return "/jk/seller/login";
        }

    }
    /* --------------------------- 회원가입 (Mybatis) ----------------------------- */

    // http://127.0.0.1:5959/SOBUN/seller/join.do
    @GetMapping(value = "/join.do")
    public String joinGET(@AuthenticationPrincipal User user, Model model) {
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
        seller.setAddress(address2 + " " + address3 + address4); // 우편번호는 제외
        seller.setPw(bcpe.encode(seller.getPw()));
        int ret = sSellerService.joinSeller(seller);

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
            return "jk/seller/login";
        }
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
        int ret = sSellerService.findSellerPw(seller); // 필요한 정보 : no, newPw, email
        log.info("Find password 2 => {}", seller.toString());
        if (ret == 1) {
            mailController.sendPwMail(seller.getEmail(), tempPw);
            return "/jk/seller/login";
        }
        return "redirect:/seller/findpw.do";
    }

    /*
     * ----------------------------- 마이페이지(Jpa) ----------------------------------
     */

    // -------------------- 마이페이지 본인인증 (비밀번호) ------------------- //
    // http://127.0.0.1:5959/SOBUN/seller/pwcheck.do
    // (아이디, 비밀번호 필요)
    @GetMapping(value = "/pwinfocheck.do")
    public String infopwcheckGET(@AuthenticationPrincipal User user, Model model) { // Security로 정보 받아옴.
        log.info("pwinfocheck get => {}", user);
        SellerEntity seller = sSellerService.findByNo(user.getUsername());
        log.info("pwinfocheck get2 => {}", seller.toString());
        model.addAttribute("seller", seller);
        model.addAttribute("companyName", seller.getName().toString());
        return ("jk/seller/mypage/pwinfocheck");
        // 미리 get에 해당 업체의 정보를 템플릿에 담아서 띄움.
    }

    // ------------------------- 정보변경 -------------------------- //
    // http://127.0.0.1:5959/SOBUN/seller/updateinfo.do
    // 1. 정보변경 (이름, 전화번호, 이메일, 주소)
    @GetMapping(value = "/updateinfo.do")
    public ModelAndView updateInfoGET(@AuthenticationPrincipal User user, Model model) { // Security로 정보 받아옴.
        SellerEntity seller = sSellerService.findByNo(user.getUsername());
        model.addAttribute("companyName", seller.getName().toString());
        model.addAttribute("user", user);

        return new ModelAndView("jk/seller/mypage/updateinfo", "seller", seller);
        // 미리 get에 해당 업체의 정보를 템플릿에 담아서 띄움.
    }

    @PostMapping(value = "/updateinfo.do")
    public String updateInfoPOST(@ModelAttribute SellerEntity seller,
            @RequestParam(name = "address1", defaultValue = "") String address1,
            @RequestParam(name = "address2", defaultValue = "") String address2,
            @RequestParam(name = "address3", defaultValue = "") String address3,
            @RequestParam(name = "address4", defaultValue = "") String address4) {
        try {
            log.info("idInfo => {}", seller);
            log.info("Seller address update => {}", address1 + address2 + address3 + address4);

            // 세션 ID 이용하여 기존정보를 받아오기
            SellerEntity sellerOld = sSellerService.findByNo(seller.getNo());
            if (address1.equals("")) { // if 새주소값 null => 이전주소 그대로 적용
                sellerOld.setAddress(sellerOld.getAddress().toString());
            } else { // else 새주소값 입력 => 새주소 적용
                sellerOld.setAddress(address2 + " " + address3 + address4);
            }
            // 변경항목 수정
            sellerOld.setName(seller.getName());
            sellerOld.setPhone(seller.getPhone());
            sellerOld.setEmail(seller.getEmail());
            sSellerService.saveObject(sellerOld);
            return "redirect:/seller/updateinfo.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // ------------------------ 비밀번호변경 -------------------------- //
    // http://127.0.0.1:5959/SOBUN/seller/updatepw.do
    // 2. 비번변경
    @GetMapping(value = "/updatepw.do")
    public ModelAndView updatepwGET(@AuthenticationPrincipal User user, Model model) { // Security로 정보 받아옴.
        SellerEntity seller = sSellerService.findByNo(user.getUsername());
        model.addAttribute("companyName", seller.getName().toString());

        return new ModelAndView("jk/seller/mypage/updatepw", "seller", seller);
        // 미리 get에 해당 업체의 정보를 템플릿에 담아서 띄움.
    }

    @PostMapping(value = "/updatepw.do")
    public String updatepwPOST(@ModelAttribute SellerEntity seller) {
        try {
            log.info("Seller UpdatePw Who => {}", seller);
            // 세션 ID 이용하여 기존암호 받아오기
            SellerEntity sellerOld = sSellerService.findByNo(seller.getNo());
            // 기존암호가 일치한다면 새암호 & 새암호 확인 대조
            /* -- newPw와 newPwCheck 사이의 유효성검사 필요! (JS) -- */
            // 새 암호값으로 업데이트
            sellerOld.setPw(bcpe.encode(seller.getNewPw()));
            // log.info(" Seller UpdatePw Who => {}", sellerOld);
            // 저장
            sSellerService.saveObject(sellerOld);
            return "redirect:/seller/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/updatepw.do";
        }
    }

    // -------------------------- 탈퇴 ---------------------------- //
    // 탈퇴 기능은 원래 관리자에서 구현해야함. (쿠팡윙도 같은 방식을 차용.)
    // 하지만 관리자 페이지의 부재로 인해 직접 탈퇴하는 방법으로 바꿔야 할듯 함.
    // 6월1일에 기능은 빼고 칸만 남겨두기로 함.

    // http://127.0.0.1:5959/SOBUN/seller/unregister.do
    // 3. 탈퇴
    @GetMapping(value = "/unregister.do")
    public ModelAndView unregisterGET(@AuthenticationPrincipal User user, Model model) { //
        // Security로 정보 받아옴.
        SellerEntity seller = sSellerService.findByNo(user.getUsername());
        model.addAttribute("companyName", seller.getName().toString());
        model.addAttribute("user", user);

        return new ModelAndView("jk/seller/mypage/unregister", "seller", seller);
    }

    @PostMapping(value = "/unregister.do")
    public String unRegisterPOST(@AuthenticationPrincipal User user,
            @ModelAttribute SellerEntity seller) {
        log.info("Seller UnRegister Who => {}", seller.toString());
        // 회원탈퇴 전 비밀번호확인 -> 정말로 탈퇴하시겠습니까? Y/N 표시
        // 외부키 (물품)이 걸려있는 상태라면 alert 메시지 출력
        // "물품이 등록된 상태에서는 탈퇴를 진행할 수 없습니다. \n물품이 모두 삭제되었는지 확인 후 탈퇴를 진행해주세요."
        try {
            sRepository.deleteById(seller.getNo());
            if (seller.getNo() == null) { // 탈퇴 성공시에 로그인 페이지로.
                return "return:/seller/login.do";
            } else { // 실패시 다시 원위치로
                return "return:/seller/unregister.do ";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "return:/seller/home.do";
        }

    }
    /* ------------------------------------------------------------------------ */
}
