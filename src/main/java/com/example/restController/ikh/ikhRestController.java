package com.example.restController.ikh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.SellerEntity;
import com.example.entity.ikh.CancelOrderMemberView;
import com.example.entity.ikh.CompleteOrderMemberView;
import com.example.entity.ikh.ProceedOrderMemberView;
import com.example.repository.ikh.CancelOrderMemberViewRepository;
import com.example.repository.ikh.CompleteOrderMemberViewRepository;
import com.example.repository.ikh.ProceedOrderMemberViewRepository;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/seller")
@RequiredArgsConstructor
@Slf4j
public class ikhRestController {
    final CompleteOrderMemberViewRepository comvRepository;
    final ProceedOrderMemberViewRepository promvRepository;
    final CancelOrderMemberViewRepository cancellRepository;
    final JkSellerRepository sellerRepository;
    
    @GetMapping(value ="/delivery.json")
    public Map<String ,Object> deliveryGET(@AuthenticationPrincipal User user, @RequestParam(name="pnonumber") BigDecimal pnonumber){
        Map<String, Object> retMap = new HashMap<>();
        try {
            SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);
            if(pnonumber != null){
                // 완료된 공구
                List<CompleteOrderMemberView> comvlist = comvRepository.findByNoAndPno(seller.getNo(), pnonumber);
                retMap.put("table", comvlist);
                
                // 진행중인공구
                List<ProceedOrderMemberView> promvlist = promvRepository.findByNoAndPno(seller.getNo(), pnonumber);                
                retMap.put("table1", promvlist);

                // 취소된 공구
                List<CancelOrderMemberView> cancelllist = cancellRepository.findByNoAndPno(seller.getNo(), pnonumber);
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