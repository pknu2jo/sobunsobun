package com.example.restController.jk;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    
    // ------------------------ 사업자번호(id) 중복체크 (GET)-------------------------- //

    @GetMapping("/selleridcheck.json")
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

    // ------------------------ 본인인증 (GET)-------------------------- // 

    @GetMapping(value = "/sellerpwcheck.json")
    public Map<String, Object> pwCheckGET(@RequestParam("redirect") String redirect) {
        log.info(" redirect 경로 => {}",redirect.toString());
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
}