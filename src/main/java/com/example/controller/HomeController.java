package com.example.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.SalesViewProjection;
import com.example.entity.StagenderView;
import com.example.entity.TotaltableView;
import com.example.repository.DeliveryViewRepository;
import com.example.repository.SalesViewRepository;
import com.example.repository.StagenderViewRepository;
import com.example.repository.StalocationViewRepository;
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
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 통계
    final TotalgenderViewRepository tgvRepository;
    final TotallocationViewRepository tlvRepository;
    final SalesViewRepository svRepository;
    final TotaltableViewRepository tvRepository;
    final StagenderViewRepository sgvRepository;
    final StalocationViewRepository slvRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 배송    
    final DeliveryViewRepository dvRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


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
    // 전체 통계
    @GetMapping(value = "/item/search.do")
    public String searchGET(Model model){
        try {
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 성비
            // 전체 여성 인원수 구하기
            long Female = tgvRepository.countByGenderAndNo("F", "1078198143");
            // 전체 남성 인원수 구하기
            long Male = tgvRepository.countByGenderAndNo("M", "1078198143");                            

            // html로 값 넘기기
            model.addAttribute("sellername", "1078198143");
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
            // 행정구별 판매수 구하기
            for(int i = 0 ;i < array.length ;i++){
                array[i] = tlvRepository.countByNoAndLocationContaining("1078198143", locations[i]);
                total += array[i];
                if(array[i] > max){
                    max = array[i];
                }
            }        
            model.addAttribute("max", max); // 가장 높은 값
            model.addAttribute("total", total); // 전체인원수
            model.addAttribute("array", array); // 배열

            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 매출 파트
            // 전체 매출
            long all = svRepository.sumByItemprice("1078198143");
            // 월 매출
            List<SalesViewProjection> mlist = svRepository.findMonthlySales("1078198143");

            model.addAttribute("all", all); // 전체매출
            model.addAttribute("mlist", mlist); // 월 매출
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 물품 테이블 넘기기
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);            

            // 상세정보로 가기 위한 리스트 추출
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
    // 물품 하나에 대한 통계
    @GetMapping(value="/item/searchdetail.do")
    public String searchdetailGET(@RequestParam(name = "itemno") long itemno, Model model) {
        try {
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
            // 물품이름 받아오기
            StagenderView name = sgvRepository.findByItemcode(BigDecimal.valueOf(itemno));
            // 한 물품에 대한 성비구하기
            long female =  sgvRepository.countByGenderAndItemcodeAndNo("F", BigDecimal.valueOf(itemno), "1078198143");
            long male =  sgvRepository.countByGenderAndItemcodeAndNo("M", BigDecimal.valueOf(itemno), "1078198143");

            model.addAttribute("itemname", name.getItemname());
            model.addAttribute("female", female);
            model.addAttribute("male", male);

            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
            // 한 물품에 대한 행정구별 판매 구하기
            // 각 행정구를 배열에 저장
            String[] locations = {"중구","서구","동구","영도구","진구","동래구","남구","북구","해운대구","사하구","금정구","강서구","연제구","수영구","사상구"};
            // 행정구가 들어간 문자배열과 같은 길이의 정수형 배열 선언
            long[] array = new long[locations.length];
            // max값과 각 구들의 개수 구하기
            long max = 0;
            long total = 0;
            // 행정구별 판매수 구하기
            for(int i = 0 ;i < array.length ;i++){
                array[i] = slvRepository.countByNoAndItemcodeAndLocationContaining("1078198143", BigDecimal.valueOf(itemno), locations[i]);
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
            long all = svRepository.sumByItempriceAndItemno("1078198143", BigDecimal.valueOf(itemno));
            // 월 매출
            List<SalesViewProjection> mlist = svRepository.findMonthlySalesAndItemno("1078198143", BigDecimal.valueOf(itemno));

            model.addAttribute("all", all); // 전체매출
            model.addAttribute("mlist", mlist); // 월 매출
            /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

            return "/seller/item/searchdetail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }       
    }
     /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 주문
    @GetMapping(value="/order/search.do")
    public String ordersearchGET() {
        try {

            return "/seller/order/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 배송
    @GetMapping(value="/delivery/search.do")
    public String deliverysearchGET(Model model) {
        try {
            long one = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(0), "1078198143");
            long two = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(1), "1078198143");
            long three = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(2), "1078198143");
            long four = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(3), "1078198143");
            long total = one+two+three+four;
            
            // 배송상태별, 전체
            model.addAttribute("one", one);
            model.addAttribute("two", two);
            model.addAttribute("three", three);
            model.addAttribute("four", four);
            model.addAttribute("total", total);


            return "/seller/delivery/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}