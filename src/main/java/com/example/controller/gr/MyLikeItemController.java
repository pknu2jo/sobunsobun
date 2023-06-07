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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.gr.grlikeitemview;
import com.example.repository.gr.grjjimRepository;
import com.example.repository.gr.grlikeitemviewRepository;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/customer")
@RequiredArgsConstructor

public class MyLikeItemController {

    @Autowired
    ResourceLoader resourceLoader; // resources폴더의 파일을 읽기 위한 객체 생성

    final grlikeitemviewRepository grlRepository;
    final grjjimRepository grjRepository;

    // 이미지 전송용
    @Value("${default.image}")
    String DEFAULTIMAGE;
    final SePurchaseItemService piService;

    @GetMapping(value = "/mylikeitem.do")
    public String mylikeitemGET(@AuthenticationPrincipal User user, Model model,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page) {

        int pageSize = 8; // 한페이지에 나오는 아이템갯수

        if (page == 0) {
            return "redirect:/customer/mylikeitem.do?page=1";
        }

        long ret = grlRepository.countByMemid(user.getUsername());
        int totalPages = (int) Math.ceil((double) ret / pageSize);
        // model.addAttribute("pages", (ret - 1) / 8 + 1);
        int maxVisiblePages = 5; // 한 번에 보이는 최대 페이지 수

        // 현재 페이지를 기준으로 시작 페이지와 끝 페이지 계산
        int startPage = Math.max(1, page - maxVisiblePages / 2);
        int endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

        PageRequest pageRequest = PageRequest.of((page - 1), pageSize);

        List<grlikeitemview> list = grlRepository.findByMemid(user.getUsername(), pageRequest);

        log.info("찜한 상품=> {}", list.toString());

        String formattedRet;
        if (ret < 10) {
            formattedRet = "0" + ret;
        } else {
            formattedRet = String.valueOf(ret);
        }

        model.addAttribute("user", user);
        model.addAttribute("list", list);
        model.addAttribute("ret", formattedRet);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/gr/customer/mylikeitem";
    }

}
