package com.example.restController.se;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class SeRestPurchaseItemController {

    final SePurchaseItemService piService;

    // 물품이 현재 공구 중인지 확인
    @GetMapping(value = "/purchasechk.json")
    public Map<String, Object> idcheckGET(
        @RequestParam(name = "itemno") long itemno
    ) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            retMap.put("chk", piService.selectPurchaseChk(itemno));
            // log.info("공구확인 => {}", piService.selectPurchaseChk(itemno));
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }
    
}
