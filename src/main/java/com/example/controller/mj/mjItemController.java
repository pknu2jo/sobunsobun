package com.example.controller.mj;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Category;
import com.example.entity.Item;
import com.example.entity.Lcategory;
import com.example.repository.mj.ItemRepository;
import com.example.repository.mj.LcateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
public class mjItemController {

    final ItemRepository iRepository;
    final LcateRepository lRepository;

    //127.0.0.1:5959/SOBUN/seller/item/insert.do
    @GetMapping(value = "/item/management.do")
    public String managementGET(Model model, @ModelAttribute Item obj, @ModelAttribute Category obj1){
        try {
            // List<Lcategory> lcatelist = lRepository.findAll();
            // log.info("Lcate => {}",lcatelist);
            model.addAttribute("category", obj1);
            List<Item> list = iRepository.findAllByRegNoOrderByNoDesc("1248600538");
            model.addAttribute("list", list);
            return "/mj/seller/management";
        } catch (Exception e) {
            return "redirect:/seller/home.do";
        }
    }

    //127.0.0.1:5959/SOBUN/seller/item/insert.do
    @GetMapping(value = "/item/insert.do")
    public String insertGET() {
        try {
            return "/mj/seller/iteminsert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }
    @PostMapping(value = "/item/insert.do")
    public String insertPOST(@ModelAttribute Item obj){
        try {
            iRepository.save(obj);
            return "redirect:/seller/item/insert.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }

    
}
