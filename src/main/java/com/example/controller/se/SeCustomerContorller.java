package com.example.controller.se;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.SendMail;
import com.example.entity.CustomerAddressEntity;
import com.example.entity.CustomerEntity;
import com.example.entity.ItemImage;
import com.example.service.se.SeCustomerService;
import com.example.service.se.SeMailService;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class SeCustomerContorller {

    final SeCustomerService cService;
    final SePurchaseItemService piService;
    final HttpSession httpSession; // 정보 전달용 session 객체 생성
    @Autowired private SeMailService mailService; // 비밀번호 찾기 메일 전송용
    // 이미지 전송용
    @Autowired ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String DEFAULTIMAGE;
    
    // ----------------------------------------------------------------------------------------------------
    // 회원가입
    @GetMapping(value = "/join.do")
    public String joinGET(                                                 
    ) {
        try {
            return "/se/customer/join";
        } catch (Exception e) {
            e.printStackTrace();
            return "/se/customer/join";
        }
    }

    @PostMapping(value = "/join.do")
    public String joinPOST( 
        @ModelAttribute CustomerEntity customer,
        @ModelAttribute CustomerAddressEntity customerAddress
        ) {
        try {
            log.info("회원가입 => {}", customer.toString());
            log.info("회원가입 => {}", customerAddress.toString());
            
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            customer.setPw( bcpe.encode(customer.getPw()) );
            log.info("회원가입 => {}", customerAddress.getCustomer());
            customerAddress.setCustomer(customer);
            // customerAddress.getCustomer().setId(customer.getId()); // 왜 안되지..?
            cService.joinCustomerOne(customer, customerAddress);
    
            return "redirect:/customer/login.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }

    // ----------------------------------------------------------------------------------------------------
    // 로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        try {
            return "/se/customer/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "/se/customer/login";
        }
    }

    // 카카오로그인
    @GetMapping(value="/kakaologin.do")
    public String kakaologinGET(
        @RequestParam(required = false) String code
    ) {
        try {
            System.out.println("======================================");
            System.out.println("살려줘요");
            System.out.println(code);
            return "/se/customer/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "/se/customer/login";
        }
    }

    // ----------------------------------------------------------------------------------------------------
    // 아이디찾기
    @GetMapping(value="/findid.do")
    public String findidGET() {
        try {
            return "/se/customer/findid";
        } catch (Exception e) {
            e.printStackTrace();
            return "/se/customer/findid";
        }
    }
    
    @PostMapping(value="/findid.do")
    public String findidPOST( @ModelAttribute CustomerEntity customer, Model model ) {
        try {
            log.info("아이디찾기 => {}", customer.toString());
            CustomerEntity obj = cService.findId(customer);
            if(obj != null) {
                log.info("아이디찾기결과 => {}", obj.toString());
                httpSession.setAttribute("name", obj.getName());
                httpSession.setAttribute("id", obj.getId());
                return "redirect:/customer/findidok.do";
            }
            else {
                model.addAttribute("message", "일치하는 회원정보를 찾을 수 없습니다");
                model.addAttribute("url", "findid.do");
                return "/se/sealert";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findid.do";
        }
    }

    // 아이디찾기 - 결과화면
    @GetMapping(value="/findidok.do")
    public String findidokGET(
        Model model
    ) {
        try {
            model.addAttribute("name", httpSession.getAttribute("name"));
            model.addAttribute("id", httpSession.getAttribute("id"));
            return "/se/customer/findidok";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findid.do";
        }
    }
    
    // ----------------------------------------------------------------------------------------------------
    // 비밀번호 찾기
    @GetMapping(value="/findpw.do")
    public String findpwGET() {
        try {
            return "/se/customer/findpw";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findpw.do";
        }
    }

    @PostMapping(value="/findpw.do")
    public String findpwPOST( @ModelAttribute CustomerEntity customer, Model model ) {
        try {

            log.info("비밀번호찾기 => {}", customer.toString());
            CustomerEntity obj = cService.findPw(customer.getEmail(), customer.getPhone());
            
            if(obj != null) {
                log.info("비밀번호찾기결과 => {}", obj.toString());

                // 임시 비밀번호 생성 -------------------------------------------------
                char[] charSet = new char[] { // 비밀번호에 포함될 문자열
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    '!', '@', '#', '$', '%', '^', '&'
                };

                StringBuffer sb = new StringBuffer(); // 임시비밀번호를 저장하기 위한 객체 생성
                SecureRandom sr = new SecureRandom(); // 난수 생성을 위한 객체 생성
                sr.setSeed(obj.getPw().getBytes()); // 난수 생성을 위한 초기값 설정

                int idx = 0;
                int len = charSet.length;
                for(int i = 0; i<15; i++){
                    // idx = (int) (len*Math.random()); // 난수 생성 방법 1)
                    idx = sr.nextInt(len); // 난수 생성 방법 2) 더 강력함
                    sb.append(charSet[idx]);
                }
                // 임시 비밀번호 생성

                // DB 데이터를 임시 비밀번호로 변경 -------------------------------------------------
                BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                obj.setPw(bcpe.encode(sb));
                cService.saveCustomer(obj);
                // DB 데이터를 임시 비밀번호로 변경

                // 임시 비밀번호를 메일로 전송 -------------------------------------------------
                SendMail mail = new SendMail();
                mail.setAddress(obj.getEmail());
                mail.setTitle(obj.getName()+"님 임시 비밀번호를 확인해주세요");
                mail.setMessage("발급된 임시 비밀번호는" + sb + "입니다");
                mailService.sendMail(mail);
                // 임시 비밀번호를 메일로 전송

                return "redirect:/customer/findpwok.do";
            }
            else {
                model.addAttribute("message", "일치하는 회원정보를 찾을 수 없습니다");
                model.addAttribute("url", "findpw.do");
                return "/se/sealert";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findpw.do";
        }
    }

    // 비밀번호찾기 - 결과화면
    @GetMapping(value="/findpwok.do")
    public String findpwokGET(
        // Model model
    ) {
        try {
            // model.addAttribute("name", httpSession.getAttribute("name"));
            // model.addAttribute("id", httpSession.getAttribute("id"));
            return "/se/customer/findpwok";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/findpw.do";
        }
    }

    // ----------------------------------------------------------------------------------------------------
    // 홈화면
    @GetMapping(value = "/home.do")
    public String homeGET( Model model ) {
        try {

            // 공구가 많이 열린 물품 목록 => 비로그인 시에만 세팅
            List<Map<String, Object>> manyList = piService.selectManyPurchaseItem();
            log.info("공구가 많이 열린 물품 => {}", manyList.toString());
            for ( Map<String, Object> manyMap : manyList ) {
                // System.out.println( ((BigDecimal) map.get("PRICE")).toPlainString() ); // 확인용
                manyMap.put("PRICE", ((BigDecimal) manyMap.get("PRICE")).toPlainString());
            }
            model.addAttribute("manyList", manyList);

            // 기한이 얼마 안 남은 공구 목록
            long selectNo = 5; // 비로그인 시 5개 // 로그인 시 8개 세팅
            List<Map<String, Object>> deadList = piService.selectDeadLinePurchaseItem(selectNo);
            log.info("기한이 얼마 안 남은 공구 => {}", deadList.toString());
            for ( Map<String, Object> deadMap : deadList ) {
                deadMap.put("PRICE", ((BigDecimal) deadMap.get("PRICE")).toPlainString());
            }
            model.addAttribute("deadList", deadList);
            
            // 내 주위 실시간 공구
            List<Map<String, Object>> aroundList = piService.selectAroundPurchaseItem("3");
            log.info("내 주위 실시간 공구 => {}", aroundList.toString());
            for ( Map<String, Object> aroundMap : aroundList ) {
                aroundMap .put("PRICE", ((BigDecimal) aroundMap.get("PRICE")).toPlainString());
            }
            model.addAttribute("aroundList", aroundList);
            
            return "/se/customer/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "/se/customer/home";
        }
    }
    
    // ----------------------------------------------------------------------------------------------------
    // 이미지 url 생성용 => 물품 번호를 보내면 대표이미지를 반환
    // 127.0.0.1:5959/customer/seimage.do?itemno=?
    @GetMapping(value = "/seimage")
    public ResponseEntity<byte[]> image ( @RequestParam(name = "itemno", defaultValue = "0" ) BigDecimal itemno) throws IOException {
        // 메뉴 번호를 입력해서 메뉴 하나 가져오기 (메뉴에 이미지 정보가 있으니까)
        ItemImage obj = piService.selectItemImageOne(itemno);

        HttpHeaders headers = new HttpHeaders(); // import org.springframework.http.HttpHeaders;
        if(obj != null){ // 이미지가 존재하는지 확인
            if(obj.getFilesize().longValue() > 0){
                headers.setContentType( MediaType.parseMediaType(obj.getFiletype()) );
                return new ResponseEntity<>( obj.getFiledata(), headers, HttpStatus.OK );
                //  == 1) ResponseEntity<byte[]> response = new ResponseEntity<>( obj.getFiledata(), headers, HttpStatus.OK );
                // 2) return response;
            }
        }
        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream(); // exception 발생 => throws IOException 처리
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>( is.readAllBytes(), headers, HttpStatus.OK );
    }

    // ----------------------------------------------------------------------------------------------------
    // 오류 페이지
    @GetMapping(value = "/403page.do")
    public String page403GET() {
        return "/error/403page";
    }

    // 이게 안되네..
    // @GetMapping(value = "/login.do?error")
    // public String page403sGET() {
    //     return "/error/403page";
    // }
    
}
