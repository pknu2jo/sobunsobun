package com.example.controller.gr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.gr.grpurchaseview;
import com.example.repository.gr.grpurchaseviewRepository;
import com.example.service.gr.GrPurchaseItemService;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
            @RequestParam(name = "page", defaultValue = "0", required = false) int page) {

        if (page == 0) {
            return "redirect:/customer/myorderlist.do?page=1";
        }

        int start = (page) * 5 - 4;
        int end = (page) * 5;

        Map<String, Object> map = new HashMap<>();

        map.put("start", start);
        map.put("end", end);
        map.put("memId", "gr9");

        // List<grpurchaseview> list = grRepository.findByMemid("gr9");/* 주문확인 할 수 있는
        // 아이디가 se4 나중에 user.username으로 변경 */
        List<grpurchaseview> list = gpiService.selectMyOrderListPage(map);
        // log.info("rkfkarkfka => {}", list.toString());

        long cnt = gpiService.countMyOrderList("gr9");

        for (int i = 0; i < list.size(); i++) {

            log.info("skdhkfk => {}", Long.parseLong(list.get(i).getPsstate().toPlainString()));
            log.info("skdhkfk1 => {}", Long.parseLong(list.get(i).getCancel().toPlainString()));

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
        model.addAttribute("list", list);
        model.addAttribute("pages", (cnt - 1) / 5 + 1);
        // log.info("rkfka list => {}", list.toString());

        return "/gr/customer/myorderlist";
    }

}
