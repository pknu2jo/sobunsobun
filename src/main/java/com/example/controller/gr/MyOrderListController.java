package com.example.controller.gr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.gr.grpurchaseview;
import com.example.repository.gr.grpurchaseviewRepository;
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
    // 이미지 전송용
    @Autowired
    ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}")
    String DEFAULTIMAGE;
    final SePurchaseItemService piService;

    @GetMapping(value = "/myorderlist.do")
    public String myorderlistGET(@AuthenticationPrincipal User user, Model model) {

        List<grpurchaseview> list = grRepository.findByMemid("se4");/* 주문확인 할 수 있는 아이디가 se4 나중에 user.username으로 변경 */
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCommaprice(Long.parseLong(list.get(i).getTotalprice().toPlainString()));
            if (list.get(i).getPsstate().longValue() == 0 && list.get(i).getCancel().longValue() == 0) {
                list.get(i).setStatechk("결제 완료");
            } else if (list.get(i).getPsstate().longValue() == 1 && list.get(i).getCancel().longValue() == 0) {
                list.get(i).setStatechk("주문 진행 중");
            } else if (list.get(i).getCancel().longValue() == 0 && list.get(i).getCancel().longValue() == 1) {
                list.get(i).setStatechk("결제 취소");
            }

        }
        model.addAttribute("list", list);
        log.info("rkfka list => {}", list.toString());

        return "/gr/customer/myorderlist";
    }

}
