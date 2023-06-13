package com.example.controller.ikh;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.SellerEntity;
import com.example.entity.ikh.SalesViewProjection;
import com.example.entity.ikh.StagenderView;
import com.example.service.ikh.IkhItemService;
import com.example.service.jk.JkSellerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
public class IkhItemdetailController {
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 통계
    final IkhItemService itemService;    
    final JkSellerService sellerRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ    
    // 물품 하나에 대한 통계
    @GetMapping(value="/item/searchdetail.do")
    public String searchdetailGET(@RequestParam(name = "itemno") long itemno, Model model,
                    @AuthenticationPrincipal User user) {
        try {
            SellerEntity seller = sellerRepository.findByNo(user.getUsername());
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);

            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
            // 물품이름 받아오기
            StagenderView name = itemService.findByItemcode(BigDecimal.valueOf(itemno));
            // 한 물품에 대한 성비구하기
            long female =  itemService.countByGenderAndItemcodeAndNo("F", BigDecimal.valueOf(itemno), seller.getNo());
            long male =  itemService.countByGenderAndItemcodeAndNo("M", BigDecimal.valueOf(itemno), seller.getNo());

            model.addAttribute("itemname", name.getItemname());
            model.addAttribute("female", female);
            model.addAttribute("male", male);

            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
            // 한 물품에 대한 행정구별 판매 구하기
            // 각 행정구를 배열에 저장
            String[] locations = {" 중구 "," 서구 "," 동구 "," 영도구 "," 진구 "," 동래구 "," 남구 "," 북구 "," 해운대구 "," 사하구 "," 금정구 "," 강서구 "," 연제구 "," 수영구 "," 사상구 "};
            // 행정구가 들어간 문자배열과 같은 길이의 정수형 배열 선언
            long[] array = new long[locations.length];
            // max값과 각 구들의 개수 구하기
            long max = 0;
            long total = 0;
            // 행정구별 판매수 구하기
            for(int i = 0 ;i < array.length ;i++){
                array[i] = itemService.countByNoAndItemcodeAndLocationContaining(seller.getNo(), BigDecimal.valueOf(itemno), locations[i]);
                total += array[i];
                if(array[i] > max){
                    max = array[i];
                }
            }
            model.addAttribute("max", max); // 가장 높은 값
            model.addAttribute("total", total); // 전체인원수
            model.addAttribute("array", array); // 배열

            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
            // 한 물품에 대한 매출 구하기
            // 전체 매출
            long all = itemService.sumByItempriceAndItemno(seller.getNo(), BigDecimal.valueOf(itemno));
            // 월 매출
            List<SalesViewProjection> mlist = itemService.findMonthlySalesAndItemno(seller.getNo(), BigDecimal.valueOf(itemno));

            model.addAttribute("all", all); // 전체매출
            model.addAttribute("mlist", mlist); // 월 매출
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

            return "/ikh/seller/item/searchdetail";
        } catch (Exception e) {
            e.printStackTrace();
            return "/jk/seller/login";
        }       
    }
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
}