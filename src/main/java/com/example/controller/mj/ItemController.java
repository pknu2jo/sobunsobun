package com.example.controller.mj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class ItemController {

    @GetMapping(value = "/insert.do")
    public String insertGET() {
        try {
            return "/admin/iteminsert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.do";
        }
    }
    @PostMapping(value = "/insert.do")
    public String insertPOST(){
        try {

            return "redirect:/sobun/insert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/home.do";
        }
    }

    
}
