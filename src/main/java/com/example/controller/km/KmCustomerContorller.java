package com.example.controller.km;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Item;
import com.example.dto.Purchase;
import com.example.service.km.KmCustomerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
public class KmCustomerContorller {
    
    @Autowired KmCustomerService customerService;

    @GetMapping(value = "/item/select.do")
    public String selectitemGET(Model model) {
        // @RequestParam(name = "no") long no 로 itemno 받기
        long no = 11; // 물품 번호

        log.info("물품 상세 조회 GET");

        // 물품 번호, 물품명, 가격, 업체명
        Item item = customerService.selectOneItem(no);
        log.info("item check => {}", item.toString());
        
        // 상품 번호에 해당하는 이미지 번호
        List<Item> imageList = customerService.selectItemImageNoList(no);
        log.info("itemimage check => {}", imageList);

        // 상품에 대한 열린 공구 가져오기 -> 남은 인원
        // long remaingPerson = customerService.countRemainingPerson(purchaseno);
        // log.info("remaingPerson => {}", remaingPerson);

        // 상품에 대한 열린 공구 가져오기 -> 공구번호, 참여인원, 마감기한, 보관소 코드, 보관소이름
        List<Purchase> purchaseList = customerService.selectPurchaseList(no);
        
        for(Purchase obj : purchaseList) {
            obj.setRemaingPerson(customerService.countRemainingPerson(obj.getNo()));
        }
        log.info("purchaseList => {}", purchaseList);

        model.addAttribute("item", item);
        model.addAttribute("imageList", imageList);
        model.addAttribute("purchaseList", purchaseList);

        return "/km/customer/selectitem";
    }


    @GetMapping(value = "/item/order.do")
    public String orderGET() {
        log.info("결제하기 GET");

        return "/km/customer/checkout";
    }







    @GetMapping(value = "/kmtest.do")
    public String testGET(Model model) {
        log.info("푸터 인클루드 테스트");

        int person = cMapper.countRemainingPerson(1004);
        log.info("person => {}", person);
        //model.addAttribute("person", person);
        return "/km/customer/sample";
    }
    
}
