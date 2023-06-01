package com.example.controller.gr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.GrDate;
import com.example.dto.Grcalender;
import com.example.entity.gr.grpurchaseview;
import com.example.repository.gr.grpurchaseviewRepository;
import com.example.service.gr.GrPurchaseItemService;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class MyOrderListController {

    final grpurchaseviewRepository grRepository;
    final GrPurchaseItemService gpiService;

    // 이미지 전송용
    @Autowired
    ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}")
    String DEFAULTIMAGE;
    final SePurchaseItemService piService;

    @GetMapping(value = "/myorderlist.do")
    public String myorderlistGET(@AuthenticationPrincipal User user, Model model,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "firstdate", defaultValue = "", required = false) String firstdate,
            @RequestParam(name = "seconddate", defaultValue = "", required = false) String seconddate) {
        try {

            // a= firstdate, b= secondate;
            int a = 0, sum = 0;

            // List<grpurchaseview> list = new ArrayList<>();
            Grcalender gc = new Grcalender();
            gc.setFirstdate(firstdate);
            gc.setSeconddate(seconddate);

            int pageSize = 5; // 한페이지에 나오는 아이템갯수
            int start1 = (page - 1) * pageSize;
            int end1 = page * pageSize;

            if (!firstdate.equals("")) {
                a = 1;
            }
            sum = a;
            if (sum == 0) {
                if (page == 0) {
                    return "redirect:/customer/myorderlist.do?page=1";
                }

                Map<String, Object> map = new HashMap<>();
                map.put("start", start1);
                map.put("end", end1);
                map.put("memId", user.getUsername());

                List<grpurchaseview> list = gpiService.selectMyOrderListPage(map);
                log.info("rkfka => {}", list.toString());

                long cnt = gpiService.countMyOrderList(user.getUsername());

                for (int i = 0; i < list.size(); i++) {
                    log.info("skdhkfk => {}",
                            Long.parseLong(list.get(i).getPsstate().toPlainString()));
                    log.info("skdhkfk1 => {}",
                            Long.parseLong(list.get(i).getCancel().toPlainString()));
                    list.get(i).setCommaprice(Long.parseLong(list.get(i).getTotalprice().toPlainString()));
                    if (Long.parseLong(list.get(i).getPsstate().toPlainString()) == 0
                            && Long.parseLong(list.get(i).getCancel().toPlainString()) == 0) {
                        list.get(i).setStatechk("결제 완료");
                    } else if (Long.parseLong(list.get(i).getPsstate().toPlainString()) == 1
                            && Long.parseLong(list.get(i).getCancel().toPlainString()) == 0) {
                        list.get(i).setStatechk("주문 진행 중");
                    } else if (Long.parseLong(list.get(i).getPsstate().toPlainString()) == 0
                            && Long.parseLong(list.get(i).getCancel().toPlainString()) == 1) {
                        list.get(i).setStatechk("결제 취소");
                    }
                }

                int totalPages = (int) ((cnt - 1) / pageSize) + 1;
                int currentSet = (int) Math.ceil((double) page / 5);
                int startPage = (currentSet - 1) * 5 + 1;
                int endPage = Math.min(startPage + 4, totalPages);

                model.addAttribute("list", list);
                model.addAttribute("pages", totalPages);
                model.addAttribute("gc", gc);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("currentPage", page);
                log.info("rkfkarkfka => ", list.toString());

            } else if (sum == 1) {

                // 날짜 사이에 있는 조회값 list = findByRegdateBetween(firstdate, seconddate)
                // 날짜 형변환
                Date date1 = new Date();
                Date date2 = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                date1 = dateFormat.parse(firstdate);
                date2 = dateFormat.parse(seconddate);
                log.info("선택날짜1 => {}", firstdate);
                log.info("선택날짜2 => {}", seconddate);
                log.info("날짜날짜1 => {}", date1);
                log.info("날짜날짜2 => {}", date2);

                Calendar calendar = Calendar
                        .getInstance();
                calendar.setTime(date1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                date1 = calendar.getTime();

                calendar.setTime(date2);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                date2 = calendar.getTime();

                log.info("date1 => {}", date1);
                log.info("date2 => {}", date2);

                GrDate grdate = new GrDate();
                grdate.setStart(start1);
                grdate.setEnd(end1);
                grdate.setFirstdate(date1);
                grdate.setSeconddate(date2);
                grdate.setMemId(user.getUsername());

                if (page == 0) {
                    return "redirect:/customer/myorderlist.do?page=1&firstdate=" + firstdate + "&seconddate="
                            + seconddate;
                }

                List<grpurchaseview> list = gpiService.searchMyOrderList(grdate);
                log.info("rkfkarkfka => {}", list);

                long cnt = gpiService.countMyOrderListDate(grdate);
                log.info("rkfka cnt => {}", cnt);

                for (int i = 0; i < list.size(); i++) {

                    log.info("skdhkfk => {}",
                            Long.parseLong(list.get(i).getPsstate().toPlainString()));
                    log.info("skdhkfk1 => {}",
                            Long.parseLong(list.get(i).getCancel().toPlainString()));

                    list.get(i).setCommaprice(Long.parseLong(list.get(i).getTotalprice().toPlainString()));
                    if (Long.parseLong(list.get(i).getPsstate().toPlainString()) == 0
                            && Long.parseLong(list.get(i).getCancel().toPlainString()) == 0) {
                        list.get(i).setStatechk("결제 완료");
                    } else if (Long.parseLong(list.get(i).getPsstate().toPlainString()) == 1
                            && Long.parseLong(list.get(i).getCancel().toPlainString()) == 0) {
                        list.get(i).setStatechk("주문 진행 중");
                    } else if (Long.parseLong(list.get(i).getPsstate().toPlainString()) == 0
                            && Long.parseLong(list.get(i).getCancel().toPlainString()) == 1) {
                        list.get(i).setStatechk("결제 취소");
                    }

                }
                int totalPages = (int) ((cnt - 1) / pageSize) + 1;
                int currentSet = (int) Math.ceil((double) page / 5);
                int startPage = (currentSet - 1) * 5 + 1;
                int endPage = Math.min(startPage + 4, totalPages);

                model.addAttribute("list", list);
                model.addAttribute("pages", totalPages);
                model.addAttribute("firstdate", firstdate);
                model.addAttribute("seconddate", seconddate);
                model.addAttribute("gc", gc);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("currentPage", page);
                log.info("rkfkarkfka => ", list.toString());

            }

            return "/gr/customer/myorderlist";

        } catch (

        Exception e) {
            e.printStackTrace();
            return "/gr/customer/mypage";
        }

    }

}
