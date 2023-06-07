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

@Controller
@RequestMapping(value = "/customer/item")
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
        @RequestParam(name = "orderby", defaultValue = "regdate", required = false) String orderby,

        @RequestParam(name = "page", defaultValue = "1", required = false) int page
    ) {
        try {

            int pageSize = 16; // 한 페이지에 나오는 물품 개수
            int start = (page*pageSize) - (pageSize-1); // 한 페이지에 나올 물품의 시작번호 (매퍼용)
            int end = page*pageSize; // 한 페이지에 나올 물품의 끝번호 (매퍼용)
            long pages = 1L; // 총 페이지 수를 저장할 변수 (화면용)

            // log.info("물품목록 param search, scode, orderby => {}, {}, {}", search, scode, orderby);
            List<SeSelectItemListView> list = new ArrayList<>();
            SeSelectItemListView obj = new SeSelectItemListView();
            obj.setSearch(search);
            obj.setScode(scode);
            obj.setOrderby(orderby);
            obj.setSort("DESC");
            obj.setStart(start);
            obj.setEnd(end);

            if(orderby.equals("price")) {
                obj.setSort("ASC"); // 정렬 기준이 price 일 때 ASC
            }

            // [menu=1 검색어]
            if(!search.equals("")) { // 검색어가 있을 때
                // log.info("검색 정렬 확인 => {}", obj.toString());
                list = piService.selectSearchItem(obj);
                // log.info("물품목록 검색 => {}", list.toString());
                pages = piService.selectSearchItemCnt(obj);
                // log.info("검색 페이지수 => {}", pages);
                model.addAttribute("searchcnt", pages);
                model.addAttribute("menu", 1);
            }

            // [menu=2 소분류]
            else if(search.equals("") && scode!=0) { // 검색어가 없고 소분류가 있을 때 => 1) 소분류 별 목록 / 2) Best 목록 / 3) 소분류에 해당하는 중분류 대분류
                // log.info("소분류 정렬 확인 => {}", obj.toString());
                list = piService.selectScodeItem(obj); // 1)
                // log.info("물품목록 소분류 => {}", list.toString());
                
                List<SeSelectItemListView> bestlist = piService.selectScodeItemBest(scode); // 2)
                model.addAttribute("bestlist", bestlist);
                // log.info("물품목록 소분류 BEST => {}", bestlist.toString());
                pages = piService.selectScodeItemCnt(obj);
                // log.info("소분류 페이지수 => {}", pages);
                
                CategoryAll cate = cateService.selectByScode(scode); // 3)
                model.addAttribute("cate", cate);
                
                model.addAttribute("menu", 2);
            }

            // [menu=3 전체목록]
            else if(search.equals("") && scode==0) { // 검색어가 없고 소분류도 없을 때 => 전체 물품 출력
                // log.info("전체 정렬 확인 => {}", obj.toString());
                list = piService.selectSearchItem(obj);
                // log.info("물품목록 전체 => {}", list.toString());
                pages = piService.selectSearchItemCnt(obj);
                // log.info("전체목록 페이지수 => {}", pages);

                List<SeManyPurchaseItemView> bestlist = piService.selectManyPurchaseItem1(6);
                model.addAttribute("bestlist", bestlist);
                model.addAttribute("menu", 3);
            }

            // 페이지네이션
            int currentSet = (int) Math.ceil((double) page / 5); // 현재 페이지의 페이지 세트 번호 (1~5페이지 => 1세트, 6~10페이지 => 2세트)
            int startPage = (currentSet - 1) * 5 + 1; // 현재 페이지 세트의 시작 페이지 번호(1세트는 1페이지, 2세트는 6페이지)
            int endPage = Math.min(startPage + 4, (int)((pages-1)/pageSize + 1)); // 현재 페이지 세트의 끝 페이지 번호(1세트는 5페이지, 2세트는 10페이지)
            // Math.min(값1, 값2) => 두 개의 값 중 더 작은 값을 반환
            // 마지막 페이지 번호를 총 페이지 수로 세팅
            // 예를 들어
            // 총 페이지 수가 23개 일 때, 현재 페이지가 22번 이면 currentSet 은 5
            // 5세트의 시작 페이지 번호는 (5 - 1) * 5 + 1 => 21
            // 5세트의 끝 페이지 번호는 21 + 4 => 25 이지만 총 페이지 수(23)보다 크기 때문에
            // Math.min 으로 총 페이지 수(23) 을 끝 페이지 번호로 세팅

            // log.info("이게 왜 안돼... => {}, {}, {}, {}", pages, currentSet, startPage, endPage);
            
            model.addAttribute("pages", (pages-1)/pageSize + 1); // 총 페이지 수
            model.addAttribute("currentPage", page); // 현재 페이지
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            // 페이지네이션
            
            model.addAttribute("list", list); 
            model.addAttribute("user", user);
            return "/se/customer/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }
    
}
