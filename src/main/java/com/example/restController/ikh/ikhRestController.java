package com.example.restController.ikh;

import java.math.BigDecimal;
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
import com.example.service.ikh.IkhOrderService;
import com.example.service.jk.JkSellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/seller")
@RequiredArgsConstructor
public class ikhRestController {
    final IkhOrderService orderService; //        
    final JkSellerService sellerRepository;
    
    @GetMapping(value ="/delivery.json")
    public Map<String ,Object> deliveryGET(@AuthenticationPrincipal User user, @RequestParam(name="pnonumber") BigDecimal pnonumber){
        Map<String, Object> retMap = new HashMap<>();
        try {
            SellerEntity seller = sellerRepository.findByNo(user.getUsername());
            if(pnonumber != null){
                // 완료된 공구
                List<CompleteOrderMemberView> comvlist = orderService.findByNoAndPnoCom(seller.getNo(), pnonumber);
                retMap.put("table", comvlist);
                
                // 진행중인공구
                List<ProceedOrderMemberView> promvlist = orderService.findByNoAndPnoPro(seller.getNo(), pnonumber);                
                retMap.put("table1", promvlist);

                // 취소된 공구
                List<CancelOrderMemberView> cancelllist = orderService.findByNoAndPnoCan(seller.getNo(), pnonumber);
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