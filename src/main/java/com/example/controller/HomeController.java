package com.example.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.SalesViewProjection;
import com.example.entity.TotaltableView;
import com.example.repository.SalesViewRepository;
import com.example.repository.StagenderViewRepository;
import com.example.repository.TotalgenderViewRepository;
import com.example.repository.TotallocationViewRepository;
import com.example.repository.TotaltableViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    final TotalgenderViewRepository tgvRepository;
    final TotallocationViewRepository tlvRepository;
    final SalesViewRepository svRepository;
    final TotaltableViewRepository tvRepository;
    final StagenderViewRepository sgvRepository;

    @GetMapping(value = "/home.do")
    public String homeGET(){
        try {
            return "/seller/home";    
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }        
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    @GetMapping(value = "/item/search.do")
    public String searchGET(Model model){
        try {
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */        
            // 전체 여성 인원수 구하기
            long Female = tgvRepository.countByGender("F");
            // 전체 남성 인원수 구하기
            long Male = tgvRepository.countByGender("M");

            // 판매자 별 여성 남성 구하기
            // long Female = tgvRepository.countByGenderAndNo("F", "사업자등록번호");
            // long Male = tgvRepository.countByGenderAndNo("M", "사업자등록번호");                

            // html로 값 넘기기
            model.addAttribute("female", Female);
            model.addAttribute("male", Male);
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 각 행정구를 배열에 저장
            String[] locations = {"중구","서구","동구","영도구","진구","동래구","남구","북구","해운대구","사하구","금정구","강서구","연제구","수영구","사상구"};
            // 행정구가 들어간 문자배열과 같은 길이의 정수형 배열 선언
            long[] array = new long[locations.length];
            // max값과 각 구들의 개수 구하기
            long max = 0;
            long total = 0;
            for(int i = 0 ;i < array.length ;i++){
                array[i] = tlvRepository.countByLocationContaining(locations[i]);
                total += array[i];
                if(array[i] > max){
                    max = array[i];
                }
            }        
            model.addAttribute("max", max); // 가장 높은 값
            model.addAttribute("total", total); // 전체인원수
            model.addAttribute("array", array); // 배열
            
            // 실제로 적용되야 할 것
            // for(int i = 0 ;i < array.length ;i++){
            //     array[i] = tlvRepository.countByNoAndLocationContaining("사업자등록번호", locations[i]);                 
            //     if(array[i] > max){
            //         max = array[i];
            //     }
            // }  

            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 매출 파트
            long all = svRepository.sumByItemprice();
            
            List<SalesViewProjection> mlist = svRepository.findMonthlySales();

            model.addAttribute("all", all);
            model.addAttribute("mlist", mlist);
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 물품 테이블 넘기기
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);            

            List<TotaltableView> tlist = tvRepository.findByNo("1078198143");
            for(TotaltableView obj : tlist){
                obj.setItemregdate(sourceFormat.parse(obj.getItemregdate().toString()));                
            }            
            model.addAttribute("tlist", tlist);

            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */        
            return "/seller/item/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:home.do";
        }        
    }
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    @GetMapping(value="/item/searchdetail.do")
    public String searchdetailGet(@RequestParam(name = "itemno") long itemno, Model model) {
        try {
            long Female =  sgvRepository.countByGenderAndItemcodeAndNo("M", BigDecimal.valueOf(itemno), "1078198143");
            long Male =  sgvRepository.countByGenderAndItemcodeAndNo("F", BigDecimal.valueOf(itemno), "1078198143");

            log.info("보이{}", Female);
            log.info("걸{}", Male);

            model.addAttribute("Female", Female);
            model.addAttribute("Male", Male);

            return "/seller/item/searchdetail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:home.do";
        }
    }
}