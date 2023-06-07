package com.example.controller.ikh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.SellerEntity;
import com.example.entity.ikh.CancelOrderView;
import com.example.entity.ikh.CompleteOrderView;
import com.example.entity.ikh.OrderView;
import com.example.entity.ikh.ProceedOrderFinalView;
import com.example.repository.ikh.CancelOrderViewRepository;
import com.example.repository.ikh.CompleteOrderViewRepository;
import com.example.repository.ikh.OrderViewRepository;
import com.example.repository.ikh.ProceedOrderFinalViewRepository;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
@Slf4j
public class IkhOrderController {
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 주문
    final OrderViewRepository ovRepository;
    final CompleteOrderViewRepository covRepository;
    final ProceedOrderFinalViewRepository porfvRepository;
    final CancelOrderViewRepository cancelRepository;    
    final JkSellerRepository sellerRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 주문
    @GetMapping(value="/order/search.do")
    public String ordersearchGET(Model model, @AuthenticationPrincipal User user,
            @RequestParam(name="pnonumber", defaultValue = "") BigDecimal pnonumber) {
        try {
            SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);

            List<OrderView> alist = ovRepository.findByNo(seller.getNo());
            model.addAttribute("alist", alist);
            List<OrderView> zlist = ovRepository.findByNoAndState(seller.getNo(), BigDecimal.valueOf(0));
            model.addAttribute("zlist", zlist);            
            List<OrderView> olist = ovRepository.findByNoAndState(seller.getNo(), BigDecimal.valueOf(1));
            model.addAttribute("olist", olist);            
            List<OrderView> tlist = ovRepository.findByNoAndState(seller.getNo(), BigDecimal.valueOf(2));
            model.addAttribute("tlist", tlist);
            List<OrderView> thlist = ovRepository.findByNoAndState(seller.getNo(), BigDecimal.valueOf(3));
            model.addAttribute("thlist", thlist);

            // 공구진행중 테이블
            long suma = 0;
            long sum0 = 0;
            long sum1 = 0;
            long sum2 = 0;            

            // 공구진행중 테이블
            List<ProceedOrderFinalView> porfvlist = porfvRepository.findByNo(seller.getNo());
            model.addAttribute("porfvlist", porfvlist);            
            sum0 = porfvlist.size();
            model.addAttribute("sum0", sum0);

            // 공구완료 테이블
            List<CompleteOrderView> covlist = covRepository.findByNo(seller.getNo());
            model.addAttribute("covlist", covlist);
            sum1 = covlist.size();
            model.addAttribute("sum1", sum1);

            // 공구취소 테이블
            List<CancelOrderView> cancellist = cancelRepository.findByNo(seller.getNo());            
            model.addAttribute("cancellist", cancellist);
            sum2 = cancellist.size();
            model.addAttribute("sum2", sum2);

            suma = sum0 + sum1 + sum2;
            model.addAttribute("suma", suma);

            List<Object> alllist = new ArrayList<>();
            alllist.addAll(porfvlist);
            alllist.addAll(covlist);
            alllist.addAll(cancellist);            
            model.addAttribute("alllist", alllist);
            
            return "/ikh/seller/order/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "/jk/seller/login";
        }
    }
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
}
