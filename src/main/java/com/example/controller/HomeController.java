package com.example.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.DeliveryEntity;
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
                @RequestParam(name="itemcode", defaultValue = "") BigDecimal itemcode,
                @RequestParam(name="itemname", defaultValue = "") String itemname,
                @RequestParam(name="address", defaultValue = "") String address,
                @RequestParam(name="purchaseno", defaultValue = "") BigDecimal purchaseno,
                @RequestParam(name="status", defaultValue = "") BigDecimal status,
                @RequestParam(name="firstdate", defaultValue = "") String firstdate,
                @RequestParam(name="seconddate", defaultValue = "") String seconddate){        
        try {            
            // log.info("물품 코드 => {}", itemcode);
            // log.info("물품 이름 => {}", itemname);
            // log.info("주소 => {}", address);
            // log.info("공구주문번호 => {}", purchaseno);
            // log.info("status {}", status);
            // log.info("firstdate {}", firstdate);
            // log.info("seconddate {}", seconddate);            
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
            // log.info("전체 => {}", deliveryview.toString());
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
            Date date1 = new Date();
            Date date2 = new Date();

            if(!firstdate.equals("") && !seconddate.equals("")){
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date1 = dateFormat.parse(firstdate);
                date2 = dateFormat.parse(seconddate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                date1 = calendar.getTime();

                calendar.setTime(date2);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                date2 = calendar.getTime();

                log.info("date1 {}", date1);
                log.info("date2 {}", date2);
            }

            
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            // 테이블             
            int a=0, b=0, c=0, d=0, e=0, f=0;            
            //sum 구하기 (입력값이 있냐 없냐 확인)
            if(itemcode != null){
                a=1;                    
            }
            if(!itemname.equals("")){
                b=1;
            }
            if(!address.equals("")){
                c=1;
            }
            if(purchaseno != null){
                d=1;
            }                
            if(status != null){
                e=1;
            }                   
            if(!firstdate.equals("")){
                f=1;
            }
            int sum = a+b+c+d+e+f;            
            log.info("sum => {}", sum);

            List<DeliveryView> list = new ArrayList<>();
            // 입력값을 토대로 검색 조건 선택
            if(sum==0){
                list = dvRepository.findByNo("1078198143");
            }
            else if(sum==1){
                if(a == 1 ){
                    list = dvRepository.findByNoAndItemcode("1078198143", itemcode);
                }
                else if(b==1){
                    list = dvRepository.findByNoAndItemnameContaining("1078198143", itemname);
                }
                else if(c==1){
                    list = dvRepository.findByNoAndAddressContaining("1078198143", address);
                }
                else if(d==1){
                    list = dvRepository.findByNoAndPurchaseno("1078198143", purchaseno);
                }
                else if(e==1){
                    list = dvRepository.findByNoAndDelivery("1078198143", status);
                }
                else if(f==1){
                    list = dvRepository.findByNoAndRegdateBetween("1078198143", date1, date2);
                }
            }
            else if(sum==2){
                if(a==1){
                    if (b==1){
                        list = dvRepository.findByNoAndItemcodeAndItemnameContaining("1078198143", itemcode, itemname);
                    }
                    else if(c==1){
                        list = dvRepository.findByNoAndItemcodeAndAddressContaining("1078198143", itemcode, address);
                    }
                    else if(d==1){
                        list = dvRepository.findByNoAndItemcodeAndPurchaseno("1078198143", itemcode, purchaseno);
                    }
                    else if(e==1){
                        list = dvRepository.findByNoAndItemcodeAndDelivery("1078198143", itemcode, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndItemcodeAndRegdateBetween("1078198143", itemcode, date1, date2);
                    }
                }
                else if(b==1){
                    if(c==1){
                        list = dvRepository.findByNoAndItemnameContainingAndAddressContaining("1078198143", itemname, address);
                    }
                    else if(d==1){
                        list = dvRepository.findByNoAndItemnameContainingAndPurchaseno("1078198143", itemname, purchaseno);
                    }
                    else if(e==1){
                        list = dvRepository.findByNoAndItemnameContainingAndDelivery("1078198143", itemname, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndItemnameContainingAndRegdateBetween("1078198143", itemname, date1, date2);
                    }
                }
                else if(c==1){
                    if(d==1){
                        list = dvRepository.findByNoAndAddressContainingAndPurchaseno("1078198143", address, purchaseno);
                    }
                    else if(e==1){
                        list = dvRepository.findByNoAndAddressContainingAndDelivery("1078198143", address, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndAddressContainingAndRegdateBetween("1078198143", address, date1, date2);
                    }
                }
                else if(d==1){
                    if(e==1){
                        list = dvRepository.findByNoAndPurchasenoAndDelivery("1078198143", purchaseno, status);
                    }
                    else if(f==1){
                        list = dvRepository.findByNoAndPurchasenoAndRegdateBetween("1078198143", purchaseno, date1, date2);
                    }
                }
                else if(e==1){
                    if(f==1){
                        list = dvRepository.findByNoAndDeliveryAndRegdateBetween("1078198143", status, date1, date2);
                    }
                }
            }
            else if(sum==3){
                if(a==1){
                    if(b==1){
                        if(c==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContaining("1078198143", itemcode, itemname, address);
                        }
                        else if(d==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchaseno("1078198143", itemcode, itemname, purchaseno);
                        }
                        else if(e==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndDelivery("1078198143", itemcode, itemname, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndRegdateBetween("1078198143", itemcode, itemname, date1, date2);
                        }
                    }
                    else if(c==1){
                        if(d==1){
                            list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchaseno("1078198143", itemcode, address, purchaseno);
                        }
                        else if(e==1){
                            list = dvRepository.findByNoAndItemcodeAndAddressContainingAndDelivery("1078198143", itemcode, address, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndAddressContainingAndRegdateBetween("1078198143", itemcode, address, date1, date2);
                        }
                    }
                    else if(d==1){
                        if(e==1){
                            list = dvRepository.findByNoAndItemcodeAndPurchasenoAndDelivery("1078198143", itemcode, purchaseno, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndPurchasenoAndRegdateBetween("1078198143", itemcode, purchaseno, date1, date2);
                        }
                    }
                    else if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndItemcodeAndDeliveryAndRegdateBetween("1078198143", itemcode, status, date1, date2);
                        }
                    }
                }
                else if(b==1){
                    if(c==1){
                        if(d==1){
                            list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchaseno("1078198143", itemname, address, purchaseno);
                        }
                        else if(e==1){
                            list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndDelivery("1078198143", itemname, address, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndRegdateBetween("1078198143", itemname, address, date1, date2);
                        }
                    }
                    else if(d==1){
                        if(e==1){
                            list = dvRepository.findByNoAndItemnameContainingAndPurchasenoAndDelivery("1078198143", itemname, purchaseno, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndPurchasenoAndRegdateBetween("1078198143", itemname, purchaseno, date1, date2);
                        }
                    }
                    else if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndDeliveryAndRegdateBetween("1078198143", itemname, status, date1, date2);
                        }
                    }
                }
                else if(c==1){
                    if(d==1){
                        if(e==1){
                            list = dvRepository.findByNoAndAddressContainingAndPurchasenoAndDelivery("1078198143", address, purchaseno, status);
                        }
                        else if(f==1){
                            list = dvRepository.findByNoAndItemnameContainingAndDeliveryAndRegdateBetween("1078198143", address, purchaseno, date1, date2);
                        }
                    }
                    else if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndAddressContainingAndDeliveryAndRegdateBetween("1078198143", address, status, date1, date2);
                        }
                    }
                }
                else if(d==1){
                    if(e==1){
                        if(f==1){
                            list = dvRepository.findByNoAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", purchaseno, status, date1, date2);
                        }
                    }
                }                
            }
            else if(sum==4){
                if(a==1){
                    if(b==1){
                        if(c==1){
                            if(d==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchaseno("1078198143", itemcode, itemname, address, purchaseno);
                            }
                            else if(e==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDelivery("1078198143", itemcode, itemname, address, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndRegdateBetween("1078198143", itemcode, itemname, address, date1, date2);
                            }
                        }
                        if(d==1){
                            if(e==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDelivery("1078198143", itemcode, itemname, purchaseno, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndRegdateBetween("1078198143", itemcode, itemname, purchaseno, date1, date2);
                            }
                        }
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndDeliveryAndRegdateBetween("1078198143", itemcode, itemname, status, date1, date2);
                            }
                        }
                    }
                    else if(c==1){
                        if(d==1){
                            if(e==1){
                                list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDelivery("1078198143", itemcode, address, purchaseno, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndRegdateBetween("1078198143", itemcode, address, purchaseno, date1, date2);
                            }
                        }
                        else if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndAddressContainingAndDeliveryAndRegdateBetween("1078198143", itemcode, address, status, date1, date2);
                            }
                        }
                    }
                    else if(d==1){
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemcodeAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", itemcode, purchaseno, status, date1, date2);
                            }
                        }
                    }
                }
                else if(b==1){
                    if(c==1){
                        if(d==1){
                            if(e==1){
                                list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery("1078198143", itemname, address, purchaseno, status);
                            }
                            else if(f==1){
                                list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween("1078198143", itemname, address, purchaseno, date1, date2);
                            }
                        }
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween("1078198143", itemname, address, status, date1, date2);
                            }
                        }
                    }
                    if(d==1){
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", itemname, purchaseno, status, date1, date2);
                            }
                        }
                    }
                }
                else if(c==1){
                    if(d==1){
                        if(e==1){
                            if(f==1){
                                list = dvRepository.findByNoAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", address, purchaseno, status, date1, date2);
                            }
                        }
                    }
                }
            }
            else if(sum==5){
                if(a==1){
                    if(b==1){
                        // 123
                        if(c==1){
                            //1234
                            if(d==1){
                                if(e==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDelivery("1078198143", itemcode, itemname, address, purchaseno, status);
                                }
                                else if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndRegdateBetween("1078198143", itemcode, itemname, address, purchaseno, date1, date2);
                                }
                            }
                            //1235
                            else if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndDeliveryAndRegdateBetween("1078198143", itemcode, itemname, address, status, date1, date2);
                                }
                            }
                        }
                        // 124
                        else if(d==1){
                            if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", itemcode, itemname, purchaseno, status, date1, date2);
                                }
                            }
                        }
                    }
                    //13
                    else if(c==1){
                        if(d==1){
                            if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemcodeAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", itemcode, address, purchaseno, status, date1, date2);
                                }
                            }
                        }
                    }
                }
                else if(b==1){
                    if(c==1){
                        if(d==1){
                            if(e==1){
                                if(f==1){
                                    list = dvRepository.findByNoAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", itemname, address, purchaseno, status, date1, date2);
                                }
                            }
                        }
                    }
                }
            }
            else{
                list = dvRepository.findByNoAndItemcodeAndItemnameContainingAndAddressContainingAndPurchasenoAndDeliveryAndRegdateBetween("1078198143", itemcode, itemname, address, purchaseno, status, date1, date2);
            }

            model.addAttribute("list", list);
            /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
            return "/ikh/seller/delivery/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }
}