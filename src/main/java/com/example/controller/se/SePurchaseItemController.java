package com.example.controller.se;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CategoryAll;
import com.example.service.se.SeCategoryService;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer/item")
@Slf4j
@RequiredArgsConstructor
public class SePurchaseItemController {

    final SePurchaseItemService piService;
    final SeCategoryService cateService;

    // ----------------------------------------------------------------------------------------------------
    // 물품목록
    @GetMapping(value = "/selectlist.do")
    public String selectGET( 
        Model model,
        @AuthenticationPrincipal User user,
        @RequestParam(name = "search", defaultValue = "", required = false) String search,
        @RequestParam(name = "scode", defaultValue = "0", required = false) long scode,
        @RequestParam(name = "orderby", defaultValue = "regdate", required = false) String orderby
    ) {
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("sort", "DESC"); // 기본 정렬 DESC // 정렬 기준이 price 일 때만 ASC

            if(!orderby.equals("regdate")) { // 정렬 기준이 있을 때
                map.put("tests", orderby);
                if(orderby.equals("price")) {
                    map.put("sort", "ASC"); // 정렬 기준이 price 일 때 ASC
                }
            }

            if(!search.equals("")) { // 검색어가 있을 때
                map.put("search", search);
                list = piService.selectSearchItem(map);
                // log.info("물품목록 검색 map => {}", map.toString());
                log.info("물품목록 검색 => {}", list.toString());
            }
            else if(search.equals("") && scode!=0) { // 검색어가 없고 소분류가 있을 때 => 1) 소분류 별 목록 / 2) Best 목록 / 3) 소분류에 해당하는 중분류 대분류
                map.put("scode", scode);
                list = piService.selectScodeItem(map); // 1)

                List<Map<String, Object>> bestlist = piService.selectScodeItemBest(scode); // 2)
                for(Map<String, Object> bestone : bestlist) {
                    bestone.put("ITEMNO", ((BigDecimal) bestone.get("ITEMNO")).toPlainString());
                    bestone.put("PRICE", ((BigDecimal) bestone.get("PRICE")).toPlainString());
                }
                model.addAttribute("bestlist", bestlist);
                
                CategoryAll cate = cateService.selectByScode(scode); // 3)
                model.addAttribute("cate", cate);
                
                // log.info("물품목록 소분류 => {}", list.toString());
                // log.info("물품목록 소분류 => {}", bestlist.toString());
            }
            else if(search.equals("") && scode==0) { // 검색어가 없고 소분류도 없을 때 => 전체 물품 출력
                list = piService.selectSearchItem(map);
                log.info("물품목록 전체 => {}", list.toString());
            }

            for(Map<String, Object> obj : list) {
                obj.put("ITEMNO", ((BigDecimal) obj.get("ITEMNO")).toPlainString());
                obj.put("PRICE", ((BigDecimal) obj.get("PRICE")).toPlainString());
            }

            model.addAttribute("list", list); 
            model.addAttribute("user", user);
            return "/se/customer/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }
    
}
