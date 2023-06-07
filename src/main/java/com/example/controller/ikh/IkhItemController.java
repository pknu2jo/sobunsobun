package com.example.controller.ikh;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.SellerEntity;
import com.example.entity.ikh.SalesViewProjection;
import com.example.entity.ikh.TotaltableView;
import com.example.repository.ikh.SalesViewRepository;
import com.example.repository.ikh.TotalgenderViewRepository;
import com.example.repository.ikh.TotallocationViewRepository;
import com.example.repository.ikh.TotaltableViewRepository;
import com.example.repository.jk.JkSellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
@Slf4j
public class IkhItemController {
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 통계
    final TotalgenderViewRepository tgvRepository;
    final TotallocationViewRepository tlvRepository;
    final TotaltableViewRepository tvRepository;
    final SalesViewRepository svRepository;
    final JkSellerRepository sellerRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 전체 통계
    @GetMapping(value = "/item/search.do")
    public String searchGET(Model model, @AuthenticationPrincipal User user){
        try {
            SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);            
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);

            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 성비
            // 전체 여성 인원수 구하기
            long Female = tgvRepository.countByGenderAndNo("F", seller.getNo());
            // 전체 남성 인원수 구하기
            long Male = tgvRepository.countByGenderAndNo("M", seller.getNo());                            
            
            log.info("{}", Female);
            log.info("{}", Male);
            // html로 값 넘기기
            if(Female != 0 || Male != 0){
                model.addAttribute("female", Female);
                model.addAttribute("male", Male);
            }
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 각 행정구를 배열에 저장
            String[] locations = {" 중구 "," 서구 "," 동구 "," 영도구 "," 진구 "," 동래구 "," 남구 "," 북구 "," 해운대구 "," 사하구 "," 금정구 "," 강서구 "," 연제구 "," 수영구 "," 사상구 "};
            // 행정구가 들어간 문자배열과 같은 길이의 정수형 배열 선언
            long[] array = new long[locations.length];
            // max값과 각 구들의 개수 구하기
            long max = 0;
            long total = 0;
            // 행정구별 판매수 구하기
            for(int i = 0 ;i < array.length ;i++){
                array[i] = tlvRepository.countByNoAndLocationContaining(seller.getNo(), locations[i]);
                total += array[i];
                if(array[i] > max){
                    max = array[i];
                }
            }
            
            if(max != 0 && total != 0){
                log.info("max {}", max);
                log.info("total {}", total);
                log.info("array {}", array);
                model.addAttribute("max", max); // 가장 높은 값
                model.addAttribute("total", total); // 전체인원수
                model.addAttribute("array", array); // 배열
            }            

            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 매출 파트
            // 전체 매출
            // 월 매출            
            List<SalesViewProjection> mlist = new ArrayList<>();
            mlist = svRepository.findMonthlySales(seller.getNo());
            // long all = 0;
            // all = svRepository.sumByItemprice(seller.getNo());
            // if(all != 0){
            //     log.info("all {}", all);
            //     model.addAttribute("all", all); // 전체매출
            // }
            if(mlist != null){             
                model.addAttribute("mlist", mlist); // 월 매출
            }            
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 물품 테이블 넘기기                       
            // 상세정보로 가기 위한 리스트 추출
            List<TotaltableView> tlist = tvRepository.findByNo(seller.getNo());
            if(tlist != null){
                model.addAttribute("tlist", tlist);
            }            
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

            return "/ikh/seller/item/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "/jk/seller/login";
        }        
    }    
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
}