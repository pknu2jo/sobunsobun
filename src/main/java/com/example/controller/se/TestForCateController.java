package com.example.controller.se;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.CategoryAll;
import com.example.mapper.se.CategoryAllMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@AllArgsConstructor
public class TestForCateController {
    
    final CategoryAllMapper cAllMapper;

    @GetMapping(value = "/testcate/1.do")
    public String test1GET(Model model){
        List<CategoryAll> llist = cAllMapper.selectLCategory();
        // log.info("대분류 카테고리 => {}", llist.toString());
        List<CategoryAll> mlist = cAllMapper.selectMCategory();
        // log.info("중분류 카테고리 => {}", mlist.toString());
        List<CategoryAll> slist = cAllMapper.selectSCategory();
        // log.info("소분류카테고리 => {}", slist.toString());
        model.addAttribute("slist", slist);
        model.addAttribute("llist", llist);
        model.addAttribute("mlist", mlist);
        return "/se/test1";
    }

}
