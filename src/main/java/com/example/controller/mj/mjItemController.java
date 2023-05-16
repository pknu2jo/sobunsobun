package com.example.controller.mj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Item;
import com.example.repository.mj.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
public class mjItemController {

    final ItemRepository iRepository;

    @GetMapping(value = "/item/insert.do")
    public String insertGET() {
        try {
            return "/mj/seller/iteminsert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }
    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Item obj){
        try {
            // iRepository.save(obj);
            return "redirect:/seller/item/insert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }

    
}
