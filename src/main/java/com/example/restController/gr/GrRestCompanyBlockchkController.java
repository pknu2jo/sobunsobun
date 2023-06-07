package com.example.restController.gr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.SellerEntity;
import com.example.repository.gr.grcompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
public class GrRestCompanyBlockchkController {

    final grcompanyRepository gcRepository;

    @PostMapping(value = "/company.json")
    public Map<String, Integer> companyPOST(
            @RequestBody Map<String, Object> map) {

        Map<String, Integer> retMap = new HashMap<>();

        try {

            String ret1 = map.get("no").toString();
            BigDecimal ret2 = BigDecimal.valueOf(Long.parseLong(map.get("blockChk").toString()));

            log.info("가람1 => {}", ret1);
            log.info("가람2 => {}", ret2);

            SellerEntity sEntity = gcRepository.findByNo(ret1);
            log.info("가람가람=>{}", sEntity);

            Long chk = Long.parseLong(map.get("blockChk").toString());
            log.info("제발 => {}", chk.toString());

            if (chk == 0) {
                sEntity.setBlockChk(BigDecimal.valueOf(1));
                gcRepository.save(sEntity);
                retMap.put("result", 1);
                retMap.put("chkstate", 0);
                log.info("아아아앙=>{}", sEntity.toString());
            } else {
                sEntity.setBlockChk(BigDecimal.valueOf(0));
                gcRepository.save(sEntity);
                retMap.put("result", 0);
                retMap.put("chkstate", 1);
                log.info("아아아아아아아=>{}", sEntity.toString());
            }

        } catch (Exception e) {
            retMap.put("result", 0);
        }
        return retMap;
    }

}