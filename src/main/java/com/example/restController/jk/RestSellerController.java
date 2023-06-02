package com.example.restController.jk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.jk.JkMailController;
import com.example.entity.SellerEntity;
import com.example.repository.jk.JkSellerRepository;
import com.example.service.jk.JkSellerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class RestSellerController {

    final JkSellerService sSellerService;
    final JkSellerRepository sRepository;
    final JkMailController mailController;
    String tempCodeChk = null;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // ------------------------ 사업자번호(id) 중복체크 (GET)-------------------------- //

    @GetMapping(value = "/selleridcheck.json")
    public Map<String, Integer> idCheckGET(@RequestParam("id") String id) {
        log.info("사업자번호 중복체크 => {}", id.toString());
        Map<String, Integer> retMap = new HashMap<>();
        try {
            int ret = sSellerService.countByNo(id);
            retMap.put("chk", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // ------------------------ 로그인 확인 (POST)-------------------------- //

    @PostMapping(value = "/sellerloginchk.json")
    public Map<String, Object> login(@RequestBody SellerEntity seller) {
        log.info("Login Chk => {}", seller.toString());
        Map<String, Object> retMap = new HashMap<>();
        try {
            if (checkCredentials(seller.getNo(), seller.getPw()) == true) { // 로그인 성공시
                // 1 반환
                retMap.put("chk", 1);
            } else { // 로그인 실패 시
                // 0 반환
                retMap.put("chk", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // DB에 아이디가 있는지를 판단하기 위해서 따로 메소드를 만들었음.
    private boolean checkCredentials(String no, String pw) {
        SellerEntity sellerDB = sRepository.findById(no).orElse(null);
        if (bcpe.matches(pw, sellerDB.getPw())) {
            return true; // 
        } else {
            return false;
        }
    }

    // ------------------------ 이메일 인증코드 발송 (POST)-------------------------- //

    @PostMapping(value = "/sellercodesend.json")
    public Map<String, Object> codeCheckPOST(@RequestBody String email) {
        log.info("Send Code To => {}", email);
        Map<String, Object> retMap = new HashMap<>();
        try {
            String tempCode = UUID.randomUUID().toString().replace("-", ""); // 난수 생성시에 - 문자는 제외함.
            tempCode = tempCode.substring(0, 6); // 6자리수의 새로운 암호 발급
            if (tempCode != "") { // 확인코드 생성시
                tempCodeChk = tempCode; //
                mailController.sendChkMail(email, tempCode);
                retMap.put("chk", 1);

            } else { // 확인코드생성 실패시
                retMap.put("chk", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // ------------------------ 이메일 인증코드 확인 (POST)-------------------------- //
    @PostMapping(value = "/sellercodecheck.json")
    public Map<String, Integer> codeCheckGET(@RequestBody String inputCode) {
        log.info("Input Code is => {}", inputCode);
        Map<String, Integer> retMap = new HashMap<>();
        try {
            String code = inputCode.replace("\"", "");// " 표시를 없앰.
            log.info("Answer Code is => {}", tempCodeChk);
            if (tempCodeChk.equals(code.toString())) {
                retMap.put("chk", 1); // 일치하면 1
            } else {
                retMap.put("chk", 0); // 불일치하면 0 출력
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // ------------------------ 본인인증 (GET)-------------------------- //

    @GetMapping(value = "/sellerpwcheck.json")
    public Map<String, Object> pwCheckGET(@RequestParam("redirect") String redirect) {
        log.info(" redirect 경로 => {}", redirect.toString());
        Map<String, Object> retMap = new HashMap<>();
        try {
            retMap.put("param", redirect);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("param", null);
        }
        return retMap;
    }

    // ------------------------ 새 비밀번호 지정 (POST)-------------------------- //

    @PostMapping(value = "/sellerpwcheck.json")
    public Map<String, Object> pwCheckPOST(@RequestBody SellerEntity seller) {
        log.info("json pwcheck Info => {}", seller);
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 세션 ID 이용하여 기존암호 받아오기
            SellerEntity sellerOld = sRepository.findById(seller.getNo()).orElse(null);
            log.info("json sellerOld => {}", sellerOld);
            // 암호화용 객체 (기존 암호와 일치여부 확인)
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            // 암호 대조(확인단계)
            if (bcpe.matches(seller.getPw(), sellerOld.getPw())) {
                // 암호가 일치하다면다음 화면으로 넘어감
                retMap.put("chk", 1);
            } else {
                // 비밀번호 불일치시 다시 화면 리턴
                retMap.put("chk", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    @PostMapping(value = "/sellerpwupdate.json")
    public Map<String, Object> pwUpdatePOST(@RequestBody SellerEntity seller) {
        log.info("json pwcheck Info => {}", seller);
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 세션 ID 이용하여 기존암호 받아오기
            SellerEntity sellerOld = sRepository.findById(seller.getNo()).orElse(null);
            log.info("json sellerOld => {}", sellerOld);
            // 암호화용 객체
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            // 암호 대조(확인단계)
            if (bcpe.matches(seller.getPw(), sellerOld.getPw())) {
                // 암호가 일치하다면다음 화면으로 넘어감
                retMap.put("chk", 1);
            } else {
                // 비밀번호 불일치시 다시 화면 리턴
                retMap.put("chk", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // ------------------------ 비밀번호 찾기 이메일 유효성 검사 (GET) --------------------------
    // //
    // 이메일을 조회만 할것이기 때문에 POST가 아닌 GET 메소드로 사용함!
    @GetMapping(value = "/sellercheckemail.json")
    public Map<String, Object> checkEmailGET(@RequestParam("id") String id, @RequestParam("email") String email) {
        log.info("Check Seller Id & Email => {}", id + ", " + email);
        Map<String, Object> retMap = new HashMap<>();
        try {
            SellerEntity ret = sRepository.findByNo(id);
            if ((ret.getEmail()).equals(email)) {
                retMap.put("chk", 1);
            } else {
                retMap.put("chk", 0);
            }
            // 아이디에 해당하는 이메일 갯수 카운트
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }
}