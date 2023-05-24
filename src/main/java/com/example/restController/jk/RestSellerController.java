package com.example.restController.jk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @GetMapping("/selleridcheck.json")
    public Map<String, Integer> idCheckGET(@RequestParam("id") String id) {
        // log.info(id.toString());
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

    @PostMapping(value = "/sellerpwcheck.json")
    public Map<String, Integer> pwCheckPost(@RequestParam("id") String id, @ModelAttribute SellerEntity seller) {
        log.info("idInfo => {}", id.toString());
        Map<String, Integer> retMap = new HashMap<>();
        try {
            // 세션 ID 이용하여 기존암호 받아오기
            SellerEntity sellerOld = sRepository.findById(seller.getNo()).orElse(null);
            // 암호화용 객체
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); 
            if (bcpe.matches(seller.getPw(), sellerOld.getPw())) { // 암호 대조(확인단계)
                // 암호가 일치하다면 새 암호값으로 업데이트함.
                /* -- newPw와 newPwCheck 사이의 유효성검사 후 넘어옴. -- */
                // 새 암호값으로 업데이트
                sellerOld.setPw(bcpe.encode(seller.getNewPw()));
                // 저장
                sRepository.save(sellerOld);
                retMap.put("chk", 1);
            } else {
                retMap.put("chk", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

}