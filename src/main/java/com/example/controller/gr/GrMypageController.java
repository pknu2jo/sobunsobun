package com.example.controller.gr;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.gr.GrCustomerService;
import com.example.service.gr.GrPurchaseItemService;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class GrMypageController {

    final GrCustomerService cService;
    final GrPurchaseItemService pService;

    // 이미지 전송용
    @Autowired
    ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}")
    String DEFAULTIMAGE;
    final SePurchaseItemService piService;

    @GetMapping(value = "/mypage.do")
    public String mypageGET(@AuthenticationPrincipal User user, Model model) {

        // 마이페이지 첫 화면 기본정보
        int ret = cService.countPurchase(user.getUsername());
        log.info("rkfka user => {}", user.getUsername());
        model.addAttribute("ret", ret);
        model.addAttribute("user", user);

        // 마이페이지 공구가 많이 열린 물품 목록
        List<Map<String, Object>> manyList = pService.selectManyPurchaseItem();

        log.info("공구가 많이 열린 물품 => {}", manyList.toString());

        DecimalFormat dc = new DecimalFormat("###,###,###,###");

        for (Map<String, Object> manyMap : manyList) {

            String str = dc.format((BigDecimal) manyMap.get("PRICE"));

            manyMap.put("PRICE", str.toString());
            manyMap.put("NAME", (manyMap.get("NAME")).toString());
            manyMap.put("ITEMNO", ((BigDecimal) manyMap.get("ITEMNO")).toPlainString());
            // manyMap.put("ITEMNO", "ldskjflsj");
            // log.info("rkfka => {}", manyMap.get("ITEMNO"));
        }
        model.addAttribute("manyList", manyList);

        return "/gr/customer/mypage";
    }

}
