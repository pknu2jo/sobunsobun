package com.example.restController.km;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.KmAdminPurchaseStatusDTO;
import com.example.entity.PurchaseEntity;
import com.example.entity.PurchaseStatusEntity;
import com.example.entity.km.KmAdminProductSimpleView;
import com.example.entity.km.KmAdminProductView;
import com.example.repository.km.KmPurchaseStatusRepository;
import com.example.repository.km.KmPurchaseStatusRepository.KmAdminPurchaseStatus;
import com.example.service.km.KmAdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
public class KmRestAdminController {
    
    final KmAdminService adminService;
    final KmPurchaseStatusRepository test;

    // 지점별 공구 가져오기
    @GetMapping(value="/purchaselistbystorage.json")
    public Map<String, Object> selectPurchaseGET(@RequestParam(name="storageno") BigDecimal storageno,
                                                @RequestParam(name="receivestate") BigDecimal receivestate) { 
        Map<String, Object> retMap = new HashMap<>();
        try {

            // 1. product.html에서 storageno, receiverstate 받아오기
            log.info("purchaselistbystorage.json => {}", storageno);
            log.info("purchaselistbystorage.json => {}", receivestate);

            List<KmAdminProductSimpleView> purchaseList = new ArrayList<>();

            // 2. storageno=?, receivestate=? => 데이터 가져오기 (kmadminproductview에서)
            if(storageno.compareTo(BigDecimal.ZERO) == 0) { // 모든 지점 선택
                purchaseList = adminService.findPurchaseByReceivestate(receivestate);
            } else {
                purchaseList = 
                    adminService.findPurchaseByStoragenoAndReceivestate(storageno, receivestate);
            }

            if(purchaseList.size() > 0) {

                
                log.info("real obj check => {}", purchaseList.toString());
                // KmPurchaseStatusProductProjection를 통해 각 공구 번호에 해당하는 아이디, state 불러오기
                for(KmAdminProductSimpleView obj : purchaseList) {
                    
                    List<KmAdminPurchaseStatus> objList = adminService.findMemidAndStateByPurchaseno(obj.getPurchaseno());
                    List<KmAdminPurchaseStatusDTO> customerList = new ArrayList<>();

                    for(KmAdminPurchaseStatus obj1 : objList) {
                        KmAdminPurchaseStatusDTO purchasestatusDTO = new KmAdminPurchaseStatusDTO();
                        purchasestatusDTO.setMemid(obj1.getMemid());
                        purchasestatusDTO.setState(obj1.getState());

                        customerList.add(purchasestatusDTO);
                    }
                    obj.setCustomerList(customerList);

                }

                retMap.put("purchaseList", purchaseList); 
                log.info("retMap check => {}", retMap.get("purchaseList"));
                // KmAdminProductView(purchaseno=1061, participant=2, receivestate=0, receivedate=2023-06-02 18:43:45.511737, storageno=11, storagename=부산남구점, memid=gr9, itemno=13, itemname=섬유탈취제 리필 640ml 2개, state=1)
            } else {
                retMap.put("purchaseList", null); 
            }

            retMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }

    // 검색어별 공구 가져오기
    @GetMapping(value="/purchaselistbysearch.json")
    public Map<String, Object> searchPurchaseGET(@RequestParam(name="searchoption") String searchoption,
                                                @RequestParam(name="searchvalue") String searchvalue) { 
        Map<String, Object> retMap = new HashMap<>();
        try {

            // 1. product.html에서 storageno, receiverstate 받아오기
            // log.info("purchaselistbystorage.json => {}", searchoption);
            // log.info("purchaselistbystorage.json => {}", searchvalue);

            // // 2. searchoption=?, searchvalue=? => 데이터 가져오기 (kmadminproductview에서)
            // if(searchoption.equals("memid")) {
            //     List<KmAdminProductView> purchaseList = test.findByPurchaseno(BigDecimal.valueOf(Long.parseLong(searchvalue)));

            // } else if(searchoption.equals("purchaseno")) {

            // }

            // log.info("search PurchaseList => {}", purchaseList.toString());

            // 3. purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기
            // if(purchaseList.size() > 0) {
            //     retMap.put("purchaseList", purchaseList); 
            //     log.info("retMap check => {}", retMap.get("purchaseList"));
                
            // } else {
            //     retMap.put("purchaseList", null); 
            // }

            retMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }

    // 고객이 물품 수령해감 => purchaseStatus의 state=2를 insert해주기
    @PostMapping(value="/insertstatus2.json")
    public Map<String, Object> searchPurchasePOST(@RequestBody PurchaseStatusEntity purchaseStatus) { 
        Map<String, Object> retMap = new HashMap<>();
        try {

            log.info("insertstatus2 => {}", purchaseStatus.toString());

            // 1. 고객이 물품을 수령해감 => purchasestatuse의 state=2인 걸로 insert
            retMap.put("statusInsertResult", adminService.insertPurchaseStatus(purchaseStatus));

            if((Integer) retMap.get("statusInsertResult") == 1) {
                // 2. purchase의 headcount를 +1로 해줌(UPDATE purchase SET HEADCOUNT = HEADCOUNT + 1 WHERE NO=1091)
                    // purchase를 불러온다.
                    PurchaseEntity purchase =  adminService.findOnePurchase(purchaseStatus.getPurchaseEntity().getNo());
                    // HEADCOUNT + 1 해준다. 
                    purchase.setHeadCount(purchase.getHeadCount().add(BigDecimal.valueOf(1)));
                    log.info("purchase 확인하기 => {}", purchase.toString());
                    // save 해준다.
                    // int ret = adminService.updatePurchaseHeadcount(purchase);
            
                // 3. headcount == participant가 되면 receivestate=1로 바꿔주기 
                log.info("purchaseEntity for headcount => {}", purchase.toString());
                if(purchase.getHeadCount().compareTo(purchase.getParticipant()) == 0) {
                    purchase.setReceiveState(BigDecimal.valueOf(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }

   
}
