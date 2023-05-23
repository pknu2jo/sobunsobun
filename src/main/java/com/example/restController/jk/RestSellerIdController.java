package com.example.restController.jk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.SellerEntity;
import com.example.service.jk.JkSellerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class RestSellerIdController {

    final JkSellerService sSellerService;
    @GetMapping("/selleridcheck.json")
    public Map<String, Integer> idCheckGET( @RequestParam("id") String id ) {
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
   
}