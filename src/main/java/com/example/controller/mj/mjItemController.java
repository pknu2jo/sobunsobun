package com.example.controller.mj;

import java.lang.ProcessBuilder.Redirect;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.type.BigDecimalType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.example.dto.Category;
import com.example.entity.Item;
import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;
import com.example.repository.mj.ItemRepository;
import com.example.repository.mj.LcateRepository;
import com.example.repository.mj.McateRepository;
import com.example.repository.mj.ScateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
public class mjItemController {

    final ItemRepository iRepository;
    final LcateRepository lRepository;
    final McateRepository mRepository;
    final ScateRepository sRepository;

    @GetMapping(value = "/item/updateimage.do")
    public String updateimageGET(){
        try {
            
            return "/mj/seller/updateimage";
        } catch (Exception e) {
            return "redirect:/seller/home.do";
        }
    }
    

/* ===========================물품관리============================================== */

    //127.0.0.1:5959/SOBUN/seller/item/management.do
    @GetMapping(value = "/item/management.do")
    public String managementGET(
        Model model, @ModelAttribute Item obj, 
        @ModelAttribute Lcategory obj1,
        @RequestParam(name = "lcate", defaultValue = "000", required = false) BigDecimal Lcode,
        @RequestParam(name = "mcate", defaultValue = "000", required = false) BigDecimal Mcode,
        @RequestParam(name = "scate", defaultValue = "000", required = false) BigDecimal Scode ){
        try {
            Category cate = new Category();
            List<Lcategory> list1 = lRepository.findAll();
            cate.setLlist(list1);  // 대분류 코드, 네임
            cate.setLcode(Lcode);  // 대분류 코드
            
            List<Mcategory> mlist = mRepository.findByLcategoryCode_code(Lcode);
            cate.setMlist(mlist);  // 중분류 코드, 네임
            cate.setMcode(Mcode);  // 중분류 코드
            List<Scategory> slist = sRepository.findByMcategoryCode_code(Mcode);
            cate.setSlist(slist);  // 소분류 코드, 네임
            cate.setScode(Scode);  // 소분류 코드
            
            log.info("cate => {}", cate);
            model.addAttribute("cate", cate);

            List<Item> list = iRepository.findAllByRegNoOrderByNoDesc("1248600538");
            // log.info("mlist => {}", mlist);
            
            // List<Item> itemSlist = iRepository.findAllByScategoryCode_code(Scode.toString());

            model.addAttribute("list", list);
            return "/mj/seller/management";
        } catch (Exception e) {
            return "redirect:/seller/home.do";
        }
    }

/* ================================물품등록======================================== */

    //127.0.0.1:5959/SOBUN/seller/item/insert.do
    @GetMapping(value = "/item/insert.do")
    public String insertGET(Model model,
        @RequestParam(name = "lcate", defaultValue = "000", required = false) BigDecimal Lcode,
        @RequestParam(name = "mcate", defaultValue = "000", required = false) BigDecimal Mcode,
        @RequestParam(name = "scate", defaultValue = "000", required = false) BigDecimal Scode ) {
        try {
            Category cate = new Category();
            List<Lcategory> list1 = lRepository.findAll();
            cate.setLlist(list1);  // 대분류 코드, 네임
            cate.setLcode(Lcode);  // 대분류 코드
            
            List<Mcategory> mlist = mRepository.findByLcategoryCode_code(Lcode);
            cate.setMlist(mlist);  // 중분류 코드, 네임
            cate.setMcode(Mcode);  // 중분류 코드
            List<Scategory> slist = sRepository.findByMcategoryCode_code(Mcode);
            cate.setSlist(slist);  // 소분류 코드, 네임
            cate.setScode(Scode);  // 소분류 코드
            
            log.info("cate => {}", cate);
            model.addAttribute("cate", cate);
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
