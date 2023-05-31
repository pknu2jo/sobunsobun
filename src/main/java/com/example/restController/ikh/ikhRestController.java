package com.example.restController.ikh;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.ikh.CompleteOrderMemberView;
import com.example.repository.ikh.CompleteOrderMemberViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/seller")
@RequiredArgsConstructor
@Slf4j
public class ikhRestController {
    final CompleteOrderMemberViewRepository comvRepository;
    
    @GetMapping(value ="/delivery.json")
    public Map<String ,Object> deliveryGET(@RequestParam(name="pnonumber") BigDecimal pnonumber){
        Map<String, Object> retMap = new HashMap<>();
        try {
            if(pnonumber != null){
                List<CompleteOrderMemberView> comvlist = comvRepository.findByNoAndPno("1078198143", pnonumber);
                retMap.put("result", comvlist);
            }            
            else {
                retMap.put("result", "비어있음");
            }            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}