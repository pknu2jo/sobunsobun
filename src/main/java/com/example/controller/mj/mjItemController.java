package com.example.controller.mj;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Category;
import com.example.entity.Item;
import com.example.entity.Lcategory;
import com.example.entity.Mcategory;
import com.example.entity.Scategory;
import com.example.entity.SellerEntity;
import com.example.entity.mj.ItemCategoryView;
import com.example.repository.jk.JkSellerRepository;
import com.example.service.jk.JkSellerService;
import com.example.service.mj.MjItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
public class mjItemController {

    final JkSellerRepository sellerRepository;
    final JkSellerService sSellerService;
    
    
    final HttpSession httpSession; //세션객체
    final MjItemService itemService;



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
                Item item = itemService.findByItemNo( BigDecimal.valueOf(no[i]) );
                item.setName(name[i]);
                item.setPrice(BigDecimal.valueOf(price[i]));
                item.setQuantity(BigDecimal.valueOf(quantity[i]));
                list.add(item);
            }
            itemService.updateItems(list);
            return "redirect:/seller/item/updateitem.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login.do";
        }
    }


    @SuppressWarnings("unchecked") // 경고 제외
    @GetMapping(value = "/item/updateitem.do")
    public String updateItemGET(Model model, @AuthenticationPrincipal User user){
        try {
            SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);
            log.info("seller => {}", seller.toString());
            log.info("sellerid => {}", seller.getNo());
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);
            
            List<BigDecimal> chk = (List<BigDecimal>) httpSession.getAttribute("itemno");
            List<Item> list =itemService.findAllByItemNo(chk);
            model.addAttribute("list", list);
            return "/mj/seller/updateitem";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login.do";
        }
    }

    @PostMapping(value = "/item/updateitem.do")
    public String updateItemPOST(@RequestParam(name = "itemno") List<BigDecimal> chk){
        try {
            httpSession.setAttribute("itemno", chk);
            return "redirect:/seller/item/updateitem.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login.do";
        }
    }


/* ===========================물품 삭제============================================== */


    @PostMapping(value ="/item/deleteitem.do")
    public String deleteitemPOST(
        @RequestParam( name = "itemno", required = false) long[] no ){
        try {
            //확인용
            if(no.length != 0){
                for(int i=0; i<no.length; i++){
                    log.info("deleteitem.do => {}", BigDecimal.valueOf(no[i]));
                }
            }
            
            int ret = itemService.deleteItemBatch(no);
            log.info("삭제된 갯수 => {}", ret);
            // 물품번호를 가져와서 물품삭제
            return "redirect:/seller/item/management.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login.do";
        }
    }

/* ===========================물품관리============================================== */

    //127.0.0.1:5959/SOBUN/seller/item/management.do
    @GetMapping(value = "/item/management.do")
    public String managementGET(
        Model model, @ModelAttribute Item obj, 
        @ModelAttribute Lcategory obj1,
        @RequestParam(name = "lcate", required = false) BigDecimal Lcode,
        @RequestParam(name = "mcate", required = false) BigDecimal Mcode,
        @RequestParam(name = "scate", required = false) BigDecimal Scode,
        @AuthenticationPrincipal User user ){
        try {

            SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);
            log.info("seller => {}", seller.toString());
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);

            Category cate = new Category();
            List<Lcategory> list1 = itemService.findAllLcategory();
            cate.setLlist(list1);  // 대분류 코드, 네임
            cate.setLcode(Lcode);  // 대분류 코드
            List<Mcategory> mlist = itemService.findByLcategoryCode_code(Lcode);
            cate.setMlist(mlist);  // 중분류 코드, 네임
            cate.setMcode(Mcode);  // 중분류 코드
            List<Scategory> slist = itemService.findByMcategoryCode_code(Mcode);
            cate.setSlist(slist);  // 소분류 코드, 네임
            cate.setScode(Scode);  // 소분류 코드
            
            log.info("cate => {}", cate);
            model.addAttribute("cate", cate);

            List<ItemCategoryView> list = new ArrayList<>();
            list = itemService.findAllByRegNoOrderByNoDesc(seller.getNo());

            // 전체 물품리스트
            if(Lcode == null && Mcode == null & Scode == null){
                list = itemService.findAllByRegNoOrderByNoDesc(seller.getNo());
            }
            // 대분류별 물품리스트
            else if( Lcode != null && Mcode == null & Scode == null ){
                // 대분류 전체 클릭시
                if(Lcode.equals(BigDecimal.valueOf(000))){
                    list = itemService.findAllByRegNoOrderByNoDesc(seller.getNo());
                }
                // 대분류 각 카테고리별 물품리스트
                else{
                    list = itemService.findAllByRegNoAndLcategoryCodeOrderByNoDesc(seller.getNo(), Lcode);
                }
            }
            // 중분류별 물품리스트
            else if( Lcode != null && Mcode != null & Scode == null ){
                // 중분류 전체 클릭시
                if(Mcode.equals(BigDecimal.valueOf(000))){
                    list = itemService.findAllByRegNoAndLcategoryCodeOrderByNoDesc(seller.getNo(), Lcode);
                }
                // 중분류 각 카테고리별 물품리스트
                else {
                    list = itemService.findAllByRegNoAndMcategoryCodeOrderByNoDesc(seller.getNo(), Mcode);
                }
            }
            // 소분류별 물품리스트
            else if(Lcode != null && Mcode != null && Scode != null){
                // 소분류 전체 클릭시
                if(Scode.equals(BigDecimal.valueOf(000))){
                    list = itemService.findAllByRegNoAndMcategoryCodeOrderByNoDesc(seller.getNo(), Mcode);
                }
                // 소분류 각 카테고리별 물품리스트
                else{
                    list = itemService.findAllByRegNoAndScategoryCodeOrderByNoDesc(seller.getNo(), Scode);
                }
            }
            model.addAttribute("list", list);
            return "/mj/seller/management";
        } catch (Exception e) {
            return "redirect:/seller/login.do";
        }
    }

/* ================================물품등록======================================== */

    //127.0.0.1:5959/SOBUN/seller/item/insert.do
    @GetMapping(value = "/item/insert.do")
    public String insertGET(Model model,  @AuthenticationPrincipal User user,
        @RequestParam(name = "lcate", defaultValue = "000", required = false) BigDecimal Lcode,
        @RequestParam(name = "mcate", defaultValue = "000", required = false) BigDecimal Mcode,
        @RequestParam(name = "scate", defaultValue = "000", required = false) BigDecimal Scode ) {
        try {
            SellerEntity seller = sSellerService.findByNo(user.getUsername());
            // SellerEntity seller = sellerRepository.findById(user.getUsername()).orElse(null);
            log.info("seller => {}", seller.toString());
            log.info("sellerid => {}", seller.getNo());
            model.addAttribute("companyName", seller.getName().toString());
            model.addAttribute("user", user);
            model.addAttribute("sellerid", seller.getNo());

            
            Category cate = new Category();
            List<Lcategory> list1 = itemService.findAllLcategory();
            cate.setLlist(list1);  // 대분류 코드, 네임
            cate.setLcode(Lcode);  // 대분류 코드
            
            List<Mcategory> mlist = itemService.findByLcategoryCode_code(Lcode);
            cate.setMlist(mlist);  // 중분류 코드, 네임
            cate.setMcode(Mcode);  // 중분류 코드
            List<Scategory> slist = itemService.findByMcategoryCode_code(Mcode);
            cate.setSlist(slist);  // 소분류 코드, 네임
            cate.setScode(Scode);  // 소분류 코드
            
            // log.info("cate => {}", cate);
            model.addAttribute("cate", cate);
            return "/mj/seller/iteminsert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login.do";
        }
    }
    
}
