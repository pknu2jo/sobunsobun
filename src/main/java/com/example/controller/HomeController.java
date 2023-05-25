package com.example.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.DeliveryEntity;
import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;
import com.example.entity.SellerEntity;
import com.example.entity.ikh.DeliveryView;
import com.example.entity.ikh.OrderView;
import com.example.entity.ikh.SalesViewProjection;
import com.example.entity.ikh.StagenderView;
import com.example.entity.ikh.TotaltableView;
import com.example.repository.DeliveryRepository;
import com.example.repository.LcategoryRepository;
import com.example.repository.McategoryRepository;
import com.example.repository.ScategoryRepository;
import com.example.repository.SellerRepository;
import com.example.repository.ikh.DeliveryViewRepository;
import com.example.repository.ikh.OrderViewRepository;
import com.example.repository.ikh.SalesViewRepository;
import com.example.repository.ikh.StagenderViewRepository;
import com.example.repository.ikh.StalocationViewRepository;
import com.example.repository.ikh.TotalgenderViewRepository;
import com.example.repository.ikh.TotallocationViewRepository;
import com.example.repository.ikh.TotaltableViewRepository;

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
    final LcategoryRepository lRepository;
    final McategoryRepository mRepository;
    final ScategoryRepository sRepository;
    final SellerRepository selRepository;
    final DeliveryRepository dRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 주문
    final OrderViewRepository ovRepository;
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // html 불러오기
    @GetMapping(value = "/home.do")
    public String homeGET(){
        try {
            return "/ikh/home";
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

            return "/ikh/seller/item/search";
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

            return "/ikh/seller/item/searchdetail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }       
    }

     /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 주문
    @GetMapping(value="/order/search.do")
    public String ordersearchGET(Model model) {
        try {
            List<OrderView> alist = ovRepository.findByNo("1078198143");
            model.addAttribute("alist", alist);
            List<OrderView> zlist = ovRepository.findByNoAndState("1078198143", BigDecimal.valueOf(0));
            model.addAttribute("zlist", zlist);            
            List<OrderView> olist = ovRepository.findByNoAndState("1078198143", BigDecimal.valueOf(1));
            model.addAttribute("olist", olist);            
            List<OrderView> tlist = ovRepository.findByNoAndState("1078198143", BigDecimal.valueOf(2));
            model.addAttribute("tlist", tlist);
            List<OrderView> thlist = ovRepository.findByNoAndState("1078198143", BigDecimal.valueOf(3));
            model.addAttribute("thlist", thlist);            

            return "/ikh/seller/order/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 배송
    @GetMapping(value="/delivery/search.do")
    public String deliverysearchGET(Model model,                          
                @RequestParam(name="status", defaultValue = "") BigDecimal status,
                @RequestParam(name="firstdate", defaultValue = "") String firstdate,
                @RequestParam(name="seconddate", defaultValue = "") String seconddate,
                @ModelAttribute DeliveryView deliveryview){
        try {
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 배송상태별, 전체
            long one = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(0), "1078198143");
            long two = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(1), "1078198143");
            long three = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(2), "1078198143");
            long four = dvRepository.countByDeliveryAndNo(BigDecimal.valueOf(3), "1078198143");
            long total = one+two+three+four;
            model.addAttribute("one", one);
            model.addAttribute("two", two);
            model.addAttribute("three", three);
            model.addAttribute("four", four);
            model.addAttribute("total", total);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 검색어
            // log.info("물품 코드 => {}", deliveryview.getItemcode());
            // log.info("물품 이름 => {}", deliveryview.getItemname());
            // log.info("주소 => {}", deliveryview.getAddress());
            // log.info("공구주문번호 => {}", deliveryview.getPurchaseno());
            // log.info("status {}", status);
            // log.info("firstdate {}", firstdate);
            // log.info("seconddate {}", seconddate);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 배송상태
            List<DeliveryEntity> dlist = dRepository.findAll();
            model.addAttribute("statuslist", dlist);            
            
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 카테고리
            // List<Lcategory> llist = lRepository.findAll();
            // List<Mcategory> mlist = mRepository.findByLcategoryCode_code(lcode);            
            // model.addAttribute("llist", llist);
            // model.addAttribute("mlist", mlist);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 기간 설정
            LocalDate today = LocalDate.now();            
            SellerEntity seller =  selRepository.findByNo("1078198143");
            LocalDate sdate =  new java.sql.Date(seller.getRegdate().getTime()).toLocalDate();
            model.addAttribute("sdate", sdate); // 업체의 생성 날짜 보내주기
            model.addAttribute("today", today); // 오늘 날짜 보내주기
            
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 테이블 
            List<DeliveryView> list = dvRepository.findByNo("1078198143");
            

            model.addAttribute("list", list);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            return "/ikh/seller/delivery/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}