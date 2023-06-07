package com.example.restController.gr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.CustomerEntity;
import com.example.repository.gr.grcustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
public class GrRestCustomerBlockchkController {

    final grcustomerRepository gcRepository;

    @PostMapping(value = "/customer.json")
    public Map<String, Integer> customerPOST(
            @RequestBody Map<String, Object> map) {

        Map<String, Integer> retMap = new HashMap<>();

        try {

            String ret = map.get("id").toString();
            String ret1 = map.get("customerId").toString();
            BigDecimal ret2 = BigDecimal.valueOf(Long.parseLong(map.get("blockchk").toString()));

            log.info("가람 => {}", ret);
            log.info("가람1 => {}", ret1);
            log.info("가람2 => {}", ret2);

            CustomerEntity cEntity = gcRepository.findById(ret1).orElse(null);
            // log.info("가람가람=>{}", cEntity);

            Long chk = Long.parseLong(map.get("blockchk").toString());

            if (chk == 0) {
                cEntity.setBlockchk(BigDecimal.valueOf(1));
                gcRepository.save(cEntity);
                retMap.put("result", 1);
                retMap.put("chkstate", 0);
            } else {
                cEntity.setBlockchk(BigDecimal.valueOf(0));
                gcRepository.save(cEntity);
                retMap.put("result", 0);
                retMap.put("chkstate", 1);
            }

        } catch (Exception e) {
            retMap.put("result", 0);
        }
        return retMap;
    }

}