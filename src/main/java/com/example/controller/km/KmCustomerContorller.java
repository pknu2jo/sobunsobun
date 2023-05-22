package com.example.controller.km;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;
import com.example.entity.ItemImage;
import com.example.service.km.KmCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class KmCustomerContorller {
    
   final KmCustomerService customerService;

// ------------------------------------------------------------------------------
    // 물품 상세 조회
    @GetMapping(value = "/item/selectone.do")
    public String selectitemGET(Model model) {
        // @RequestParam(name = "no") long no 로 itemno 받기
        // @Auth~ User로 세션 넘기기

        long no = 11; // 물품 번호 (공구 열린거)
        // long no = 13; // 물품 번호 (공구 안열린거)

        log.info("물품 상세 조회 GET");
        // ------------------------------------------------------------------------------

        // 물품 정보 가져오기
        Map<String,Object> item = customerService.selectOneItem(no);
        
        // 상품 번호에 해당하는 이미지 번호
        List<Long> imgList = customerService.selectItemImageNoList(no);

        // 상품에 대한 열린 공구 가져오기 -> 남은 인원
        List<kmPurchaseView> purchaseList = customerService.selectPurchaseList(no);
        for(Iterator<kmPurchaseView> it = purchaseList.iterator(); it.hasNext();) {
            kmPurchaseView obj = it.next();

            long remainingPerson = customerService.countRemainingPerson(obj.getPurchaseNo());
            obj.setRemainingPerson(remainingPerson);

            if(obj.getRemainingPerson() <= 0L) {
                it.remove();
            }
        }

        // 보관소 지점 가져오기
        List<Storage> storage = customerService.selectStorageList();           
        log.info("보관소 정보 storage => {}", storage.toString());
        
        model.addAttribute("purchaseList", purchaseList);
        model.addAttribute("item", item);
        model.addAttribute("imgList", imgList);
        model.addAttribute("storage", storage);
        log.info("purchaseList => {}", purchaseList);
        
        return "/km/customer/selectitem";
    }

// ---------------------------------------------------------------------------------

    // 이미지
    final ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String DEFAULTIMAGE;
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") BigDecimal no)
                throws IOException {

        ItemImage obj = customerService.findById(no);
        HttpHeaders headers = new HttpHeaders();

        if (obj != null) {
            if(obj.getFilesize().longValue() > 0L) {
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));

                return new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);
            }
        }

        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/item/order.do")
    public String orderGET() {
        log.info("결제하기 GET");

        return "/km/customer/checkout";
    }







    @GetMapping(value = "/kmtest.do")
    public String testGET(Model model) {
        log.info("푸터 인클루드 테스트");

        // int person = cMapper.countRemainingPerson(1004);
        // log.info("person => {}", person);
        //model.addAttribute("person", person);

        // long no = 5;
        // List<Map<String, Object>> purchaseList = customerService.selectPurchaseList(no);
        
        // for(Map<String, Object> obj : purchaseList) {
        //     // log.info("maplist => {}", obj.get("PURCHASENO"));
        //     obj.put("REMAININGPERSON", customerService.countRemainingPerson((BigDecimal) obj.get("PURCHASENO")));
        //     // obj.setRemaingPerson(customerService.countRemainingPerson(obj.getNo()));
        // }
        // log.info("purchaseList => {}", purchaseList);

        // model.addAttribute("list", purchaseList);
        return "/km/customer/sample";
    }
    
}
