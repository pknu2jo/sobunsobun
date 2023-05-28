package com.example.controller.se;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CategoryAll;
import com.example.dto.SeManyPurchaseItemView;
import com.example.dto.SeSelectItemListView;
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
    // 물품목록 // 검색어 menu1, 소분류 menu2, 전체 menu3
    @GetMapping(value = "/selectlist.do")
    public String selectGET( 
        Model model,
        @AuthenticationPrincipal User user,
        @RequestParam(name = "search", defaultValue = "", required = false) String search,
        @RequestParam(name = "scode", defaultValue = "0", required = false) long scode,
        @RequestParam(name = "orderby", defaultValue = "regdate", required = false) String orderby
    ) {
        try {
            log.info("물품목록 param search, scode, orderby => {}, {}, {}", search, scode, orderby);
            List<SeSelectItemListView> list = new ArrayList<>();
            SeSelectItemListView obj = new SeSelectItemListView();
            obj.setSearch(search);
            obj.setScode(scode);
            obj.setOrderby(orderby);
            obj.setSort("DESC");

            if(orderby.equals("price")) {
                obj.setSort("ASC"); // 정렬 기준이 price 일 때 ASC
            }

            // [menu=1 검색어]
            if(!search.equals("")) { // 검색어가 있을 때
                log.info("검색 정렬 확인 => {}", obj.toString());
                list = piService.selectSearchItem(obj);
                // log.info("물품목록 검색 => {}", list.toString());
                model.addAttribute("menu", 1);
            }

            // [menu=2 소분류]
            else if(search.equals("") && scode!=0) { // 검색어가 없고 소분류가 있을 때 => 1) 소분류 별 목록 / 2) Best 목록 / 3) 소분류에 해당하는 중분류 대분류
                log.info("소분류 정렬 확인 => {}", obj.toString());
                list = piService.selectScodeItem(obj); // 1)
                // log.info("물품목록 소분류 => {}", list.toString());
                
                List<SeSelectItemListView> bestlist = piService.selectScodeItemBest(scode); // 2)
                model.addAttribute("bestlist", bestlist);
                // log.info("물품목록 소분류 BEST => {}", bestlist.toString());
                
                CategoryAll cate = cateService.selectByScode(scode); // 3)
                model.addAttribute("cate", cate);
                
                model.addAttribute("menu", 2);
            }

            // [menu=3 전체목록]
            else if(search.equals("") && scode==0) { // 검색어가 없고 소분류도 없을 때 => 전체 물품 출력
                log.info("전체 정렬 확인 => {}", obj.toString());
                list = piService.selectSearchItem(obj);
                // log.info("물품목록 전체 => {}", list.toString());

                List<SeManyPurchaseItemView> bestlist = piService.selectManyPurchaseItem1(6);
                model.addAttribute("bestlist", bestlist);
                model.addAttribute("menu", 3);
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
