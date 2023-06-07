package com.example.restController.se;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.CustomerEntity;
import com.example.entity.Item;
import com.example.entity.JjimEntity;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SeRestPurchaseItemController {

    final SePurchaseItemService piService;

    // 물품이 현재 공구 중인지 확인
    @GetMapping(value = "/purchasechk.json")
    public Map<String, Object> purchasechkGET(
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
    

    // 물품이 찜 상태인지 확인
    @GetMapping(value = "/sejjimchk.json")
    public Map<String, Object> jjimchkGET(
        @RequestParam(name = "itemno") long itemno,
        @AuthenticationPrincipal User user
    ) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            long chk = piService.countByCustomerEntity_idAndItemEntity_no(user.getUsername(), BigDecimal.valueOf(itemno));
            retMap.put("chk", chk);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

    // 찜상태 변경
    @PostMapping(value = "/sejjimchange.json")
    public Map<String, Object> jjimchangePOST(
        @RequestBody Map<String, Object> map,
        @AuthenticationPrincipal User user
    ) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            // log.info("찜업뎃 => {}", map.toString());
            BigDecimal itemno = BigDecimal.valueOf((Long.valueOf(map.get("itemno").toString())));
            int menu = (int) map.get("menu");

            if(menu == 2) { // insert
                JjimEntity jjimEntity = new JjimEntity();
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.setId(user.getUsername());
                Item itemEntity = new Item();
                itemEntity.setNo(itemno);
                jjimEntity.setCustomerEntity(customerEntity);
                jjimEntity.setItemEntity(itemEntity);
                int ret = piService.saveJjim(jjimEntity);
                retMap.put("ret", ret);
            }
            else if(menu == 1) { // delete
                int ret = piService.deleteJjim(user.getUsername(), itemno);
                retMap.put("ret", ret);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("chk", -1);
        }
        return retMap;
    }

}
