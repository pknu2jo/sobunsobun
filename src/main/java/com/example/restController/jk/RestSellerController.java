package com.example.restController.jk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.jk.JkMailController;
import com.example.entity.SellerEntity;
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
    final JkMailController mailController;
    String tempCodeChk = null;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // ------------------------ 사업자번호(id) 중복체크 (GET) -------------------------- //

    @GetMapping(value = "/selleridcheck.json")
    public Map<String, Integer> idCheckGET(@RequestParam("id") String no) {
        log.info("사업자번호 중복체크 => {}", no.toString());
        Map<String, Integer> retMap = new HashMap<>();
        try {
            int ret = sSellerService.countByNo(no); // 입력된 사업자번호와 동일한 값이 있는지 
            retMap.put("chk", ret); // 없다면 0, 있다면 1 출력
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // ------------------------ 로그인 확인 (POST) -------------------------- //

    @PostMapping(value = "/sellerloginchk.json")
    public Map<String, Object> login(@RequestBody SellerEntity seller) {
        log.info("Login Chk => {}", seller.toString()); // Body로 잘 받아왔는지 Check
        Map<String, Object> retMap = new HashMap<>();
        try {
            if (checkLogin(seller.getNo(), seller.getPw()) == true) { // 로그인 성공시 (true)
                retMap.put("chk", 1); // chk값 1로 저장
            } else { // 로그인 실패 시 (false)
                retMap.put("chk", 0); // chk값 0으로 저장 
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1); // 그 이외의 오류는 -1로 저장
        }
        return retMap; // 저장된 Map 반환
    }

    // DB에 아이디가 있는지를 판단하기 위해 따로 확인하는 메소드
    private boolean checkLogin(String no, String pw) {
        SellerEntity sellerDB = sSellerService.findByNo(no); // 아이디로 DB 내용조회 후
        if (bcpe.matches(pw, sellerDB.getPw())) { // 비밀번호를 대조함.
            return true; // 일치하면 true 출력
        } else {
            return false; // 아이디조회가 안되거나 & 비밀번호가 틀린경우 모두 false로 출력
        }
    }

    // ------------------------ 이메일 인증코드 발송 (POST) -------------------------- //

    @PostMapping(value = "/sellercodesend.json")
    public Map<String, Object> codeCheckPOST(@RequestBody String email) {
        log.info("Send Code To => {}", email);
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 난수 생성시에 "-" 문자는 제외함.
            String tempCode = UUID.randomUUID().toString().replace("-", ""); 
            tempCode = tempCode.substring(0, 6); // 6자리의 인증코드 발급
            if (tempCode != "") { // 코드가 생성되면
                tempCodeChk = tempCode; // 인증확인용 변수 tempCodeChk에 대입 
                                        // (다른메소드에서 사용할 예정)
                mailController.sendChkMail(email, tempCode); // 메일 컨트롤러를 통해 메일 전송
                retMap.put("chk", 1); // 성공시 1 
            } else { 
                retMap.put("chk", 0); // 실패시 0
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1); // 오류는 -1
        }
        return retMap;
    }

    // ------------------------ 이메일 인증코드 확인 (POST) -------------------------- //
    @PostMapping(value = "/sellercodecheck.json")
    public Map<String, Integer> codeCheckGET(@RequestBody String inputCode) {
        log.info("Input Code is => {}", inputCode);
        Map<String, Integer> retMap = new HashMap<>();
        try {
            // 큰 따옴표 " 표시를 없앰. 
            // (tempCodeChk로 옮기는 과정에서 큰따옴표가 벗겨지는 현상 해결용)
            String code = inputCode.replace("\"", ""); 
            log.info("Answer Code is => {}", tempCodeChk); 
            if (tempCodeChk.equals(code.toString())) { // 인증코드 일치여부 확인
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

    // ------------------------ 본인인증용 비밀번호 입력 (GET)-------------------------- //

    @GetMapping(value = "/sellerpwcheck.json")
    public Map<String, Object> pwCheckGET(@RequestParam("redirect") String redirect) {
        // 인증후 마이페이지별 이동을 위해 인증화면 주소 이동할 페이지를 붙여줌.
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
            // 기존 암호 받아오기
            SellerEntity sellerOld = sSellerService.findByNo(seller.getNo());
            // 암호화용 객체 (기존 암호와 일치여부 확인용)
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
            // 기존 암호 받아오기
            SellerEntity sellerOld = sSellerService.findByNo(seller.getNo());
            log.info("json sellerOld => {}", sellerOld);
            // 암호화용 객체 (기존 암호와 일치여부 확인용)
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
    // 이메일을 조회만 할것이기 때문에 POST가 아닌 GET 메소드로 사용함!

    @GetMapping(value = "/sellercheckemail.json")
    public Map<String, Object> checkEmailGET(@RequestParam("id") String id, @RequestParam("email") String email) {
        log.info("Check Seller Id & Email => {}", id + ", " + email);
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 해당 Id에 대한 정보를 불러옴.
            SellerEntity ret = sSellerService.findByNo(id);
            if ((ret.getEmail()).equals(email)) { // DB의 메일정보와 입력된 메일주소가 일치하면
                retMap.put("chk", 1); // chk값 1 (임시비밀번호 전송)
            } else {                              // 불일치시
                retMap.put("chk", 0); // chk값 0 (오류메세지 출력)
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }
}