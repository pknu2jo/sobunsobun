package com.example.controller.gr;

import java.util.ArrayList;
import java.util.List;

import org.h2.expression.condition.CompareLike.LikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.LikeItem;
import com.example.service.gr.GrLikeItemService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequestMapping(value = "/customer")
public class LikeItemController {

    @Autowired
    GrLikeItemService lService; // 서비스 객체 생성

    @Autowired
    ResourceLoader resourceLoader; // resources폴더의 파일을 읽기 위한 객체 생성

    @GetMapping(value = "/mylikeitem.do")
    public String mylikeitemGET() {
        return "/gr/customer/mylikeitem";
    }

    // @PostMapping(value = "/mypage.do")
    // public String insertPOST(@RequestParam(name = "memId[]") String[] memId,
    // @RequestParam(name = "scategoryCode[]") long[] scategoryCode) {
    // List<LikeItem> list = new ArrayList<>();
    // for (int i = 0; i < memId.length; i++) {
    // LikeItem likeitem = new LikeItem();
    // likeitem.setMemId(memId[i]);
    // likeitem.setScategoryCode(scategoryCode[i]);
    // list.add(likeitem);
    // }

    // log.info("likeitem => {}", list.toString());
    // int ret = lService.insertLikeItem(list);
    // log.info("likeitem => {}", ret);
    // return "/grLikeItem";
    // }

}
