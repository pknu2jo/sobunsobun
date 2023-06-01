package com.example.restController.ikh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.ikh.CancelOrderMemberView;
import com.example.entity.ikh.CompleteOrderMemberView;
import com.example.entity.ikh.ProceedOrderMemberView;
import com.example.repository.ikh.CancelOrderMemberViewRepository;
import com.example.repository.ikh.CompleteOrderMemberViewRepository;
import com.example.repository.ikh.ProceedOrderMemberViewRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/seller")
@RequiredArgsConstructor
public class ikhRestController {
    final CompleteOrderMemberViewRepository comvRepository;
    final ProceedOrderMemberViewRepository promvRepository;
    final CancelOrderMemberViewRepository cancellRepository;
    
    @GetMapping(value ="/delivery.json")
    public Map<String ,Object> deliveryGET(@RequestParam(name="pnonumber") BigDecimal pnonumber){
        Map<String, Object> retMap = new HashMap<>();
        try {
            if(pnonumber != null){
                List<CompleteOrderMemberView> comvlist = comvRepository.findByNoAndPno("1078198143", pnonumber);
                retMap.put("table", comvlist);
                
                List<ProceedOrderMemberView> promvlist = promvRepository.findByNoAndPno("1078198143", pnonumber);
                retMap.put("table1", promvlist);

                List<CancelOrderMemberView> cancelllist = cancellRepository.findByNoAndPno("1078198143", pnonumber);
                retMap.put("table2", cancelllist);
            }            
            else {
                retMap.put("table", "비어있음");
                retMap.put("table1", "비어있음");
                retMap.put("table2", "비어있음");                
            }            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("table", -1);
            retMap.put("table1", -1);
            retMap.put("table2", -1);            
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}