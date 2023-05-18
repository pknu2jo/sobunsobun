package com.example.restController.se;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.se.SeCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class RestIdCheckController {

    final SeCustomerService cService;
    
    @GetMapping(value = "/idcheck.json")
    public Map<String, Integer> idcheckGET(
        @RequestParam(name = "id") String id
    ) {
        Map<String, Integer> retMap = new HashMap<>();
        try {
            // log.info("아이디중복확인 => {}", id);
            int ret = cService.idCheck(id);
            // log.info("아이디중복확인 => {}", ret);
            retMap.put("chk", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }


}
