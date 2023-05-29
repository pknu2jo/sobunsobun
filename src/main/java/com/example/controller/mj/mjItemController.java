package com.example.controller.mj;

import java.lang.ProcessBuilder.Redirect;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.type.BigDecimalType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
import com.example.entity.ItemImage;
import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;
import com.example.entity.mj.ItemCategoryView;
import com.example.mapper.mj.mjItemMapper;
import com.example.repository.mj.ItemCategoryViewRepository;
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
    final mjItemMapper iMapper;
    final ItemCategoryViewRepository icvRepository;
    final HttpSession httpSession; //세션객체



/* ===========================물품 수정============================================== */
    
    @PostMapping(value = "/item/updateitemaction.do")
    public String updateitemactionPOST(
        @RequestParam(name = "no[]") long[] no,
        @RequestParam(name = "name[]") String[] name,
        @RequestParam(name = "price[]") long[] price,
        @RequestParam(name = "quantity[]") long[] quantity ){
        try {
            List<Item> list = new ArrayList<>();
            for(int i=0; i<no.length; i++){
                Item item = iRepository.findById( BigDecimal.valueOf(no[i]) ).orElse(null);
                item.setName(name[i]);
                item.setPrice(BigDecimal.valueOf(price[i]));
                item.setQuantity(BigDecimal.valueOf(quantity[i]));
                list.add(item);
            }
            iRepository.saveAll(list);
            return "redirect:/seller/item/updateitem.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }

    



    @SuppressWarnings("unchecked") // 경고 제외
    @GetMapping(value = "/item/updateitem.do")
    public String updateItemGET(Model model){
        try {
            List<BigDecimal> chk = (List<BigDecimal>) httpSession.getAttribute("chk[]");
            List<Item> list =iRepository.findAllById(chk);
            model.addAttribute("list", list);
            return "/mj/seller/updateitem";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }

    @PostMapping(value = "/item/updateitem.do")
    public String updateItemPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk){
        try {
            httpSession.setAttribute("chk[]", chk);
            return "redirect:/seller/item/updateitem.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }


/* ===========================물품 삭제============================================== */


    @PostMapping(value ="/item/deleteitem.do")
    public String deleteitemPOST(
        @RequestParam (name = "chk[]", required = false) long[] no
        // @RequestParam (name = "btn", required = false) String btn
    ){
        try {
            //확인용
            for(int i=0; i<no.length; i++){
                log.info("deleteitem.do => {}", BigDecimal.valueOf(no[i]));
            }
            
            int ret = iMapper.deleteItemBatch(no);
            log.info("삭제된 갯수 => {}", ret);
            
            // iRepository.deleteAllByRegNo(no);
            
            // 물품번호를 가져와서 물품삭제
            // int ret = iRepository.deleteByNo(chk);
            // log.info("ret =>{}", ret);
            return "redirect:/seller/item/management.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }

/* ===========================이미지 등록/수정============================================== */

    // @GetMapping(value = "/item/updateimage.do")
    // public String updateimageGET(){
    //     try {
    //         return "/mj/seller/updateimage";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/seller/home.do";
    //     }
    // }
    // // @PostMapping(value = "/item/updateimage.do")
    // // public String updateimagePOST(@ModelAttribute ItemImage obj){
    // //     try {
    // //         // return "redirect:/seller/item/updateimage.do?no=" + obj.getItemNo().getNo().longValue();
    // //     } catch (Exception e) {
    // //         e.printStackTrace();
    // //         return "redirect:/seller/home.do";
    // //     }
    // // }

    

/* ===========================물품관리============================================== */

    //127.0.0.1:5959/SOBUN/seller/item/management.do
    @GetMapping(value = "/item/management.do")
    public String managementGET(
        Model model, @ModelAttribute Item obj, 
        @ModelAttribute Lcategory obj1,
        @RequestParam(name = "lcate", required = false) BigDecimal Lcode,
        @RequestParam(name = "mcate", required = false) BigDecimal Mcode,
        @RequestParam(name = "scate", required = false) BigDecimal Scode ){
        try {
            // log.info("user => {}", user.getUsername());


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

            // List<Item> list = new ArrayList<>();
            // list = iRepository.findAllByRegNoOrderByNoDesc("1248600538");
            List<ItemCategoryView> list = new ArrayList<>();
            list = icvRepository.findAllByRegNoOrderByNoDesc("1078198143");
            // if( Lcode == BigDecimal.valueOf(000) ){ // cate가 없으면?
            // }

            // 전체 물품리스트
            if(Lcode == null && Mcode == null & Scode == null){
                list = icvRepository.findAllByRegNoOrderByNoDesc("1078198143");
            }
            // 대분류별 물품리스트
            else if( Lcode != null && Mcode == null & Scode == null ){
                // 대분류 전체 클릭시
                if(Lcode.equals(BigDecimal.valueOf(000))){
                    list = icvRepository.findAllByRegNoOrderByNoDesc("1078198143");
                }
                // 대분류 각 카테고리별 물품리스트
                else{
                    list = icvRepository.findAllByRegNoAndLcategoryCodeOrderByNoDesc("1078198143", Lcode);
                }
            }
            // 중분류별 물품리스트
            else if( Lcode != null && Mcode != null & Scode == null ){
                // 중분류 전체 클릭시
                if(Mcode.equals(BigDecimal.valueOf(000))){
                    list = icvRepository.findAllByRegNoAndLcategoryCodeOrderByNoDesc("1078198143", Lcode);
                }
                // 중분류 각 카테고리별 물품리스트
                else {
                    list = icvRepository.findAllByRegNoAndMcategoryCodeOrderByNoDesc("1078198143", Mcode);
                }
            }
            // 소분류별 물품리스트
            else if(Lcode != null && Mcode != null && Scode != null){
                // 소분류 전체 클릭시
                if(Scode.equals(BigDecimal.valueOf(000))){
                    list = icvRepository.findAllByRegNoAndMcategoryCodeOrderByNoDesc("1078198143", Mcode);
                }
                // 소분류 각 카테고리별 물품리스트
                else{
                    list = icvRepository.findAllByRegNoAndScategoryCodeOrderByNoDesc("1078198143", Scode);
                }
            }
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
            
            // log.info("cate => {}", cate);
            model.addAttribute("cate", cate);
            return "/mj/seller/iteminsert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }
    

    // @PostMapping(value = "/item/insert.do")
    // public String insertPOST(@ModelAttribute Item obj ){
    //     try {

    //         log.info("obj => {}", obj.toString());
    //         iRepository.save(obj);
    //         return "redirect:/seller/item/insert.do";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/seller/home.do";
    //     }
    // }

    
}
