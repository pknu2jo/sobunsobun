package com.example.restController.km;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.PurchaseEntity;
import com.example.entity.km.KmPurchaseStatusIdProjection;
import com.example.repository.km.KmPurchaseRepository;
import com.example.service.km.KmAdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
public class KmRestAdminController {

    final KmAdminService adminService;

    final KmPurchaseRepository test;

    // 지점별, 수령 미완/완료 별 공구 리스트 불러오기
    @GetMapping(value = "/purchaselistbystorage.json")
    public Map<String, Object> selectPurchaseGET(
            @RequestParam(name = "storageno") BigDecimal storageno,
            @RequestParam(name = "receivestate") BigDecimal receivestate) {
        Map<String, Object> retMap = new HashMap<>();
        try {

            // 1. product.html에서 storageno 받아오기
            log.info("purchaselistbystorage.json => {}", storageno);

            // 2. storageno=?와 deliveryno=3(배달 완료), receivestate=?인 PURCHASE의 번호, 참여자수 가져오기
            List<PurchaseEntity> purchaseList = adminService.findPurchaseByStoragenoAndDelieveryNo(storageno,
                    receivestate);

            // 3. purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기
            if (purchaseList.size() > 0) {
                for (PurchaseEntity obj : purchaseList) {
                    log.info("purchaseNo check => {}", obj.getNo());
                    List<KmPurchaseStatusIdProjection> testMemIdList = adminService
                            .findIdByPurchaseNoAndState(obj.getNo());

                    List<String> memIdList = new ArrayList<>();

                    for (KmPurchaseStatusIdProjection obj1 : testMemIdList) {

                        log.info("memId check => {}", obj1.getCustomerEntity().getId());

                        memIdList.add(obj1.getCustomerEntity().getId());
                    }
                    obj.setMemIdList(memIdList);
                    // log.info("abcedfg => {}", obj.toString());
                }
                retMap.put("purchaseList", purchaseList);
                log.info("retMap check => {}", retMap.get("purchaseList"));
                // [PurchaseEntity(no=1057, participant=2, deadline=2023~,
                // regdate=2023~, receiveState=0, headCount=0, receivedate=2023~,
                // deliveryEntity=DeliveryEntity(no=3, status=배송완료),
                // storageEntity=StorageEntity(no=3, name=부산해운대점, phone=051-749-4000,
                // postcode=48095, address1=부산 광역시 해운대구 중동2로 11, address2=null, address3=null,
                // latitude=35.1629981, longitude=129.163735, adminEntity=AdminEntity(id=admin,
                // pw=admin, name=admin, regdate=2023-05-18 06:27:30.578523), regdate=2023-05-18
                // 07:03:17.925341),
                // memIdList=[km2, se5])]
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

    // 검색어별(공구번호, 회원아이디), 수령 미완/완료 별 공구 리스트 불러오기
    @GetMapping(value = "/purchaselistbysearch.json")
    public Map<String, Object> searchhPurchaseGET(
            @RequestParam(name = "receivestate") BigDecimal receivestate,
            @RequestParam(name = "searchoption") String searchoption,
            @RequestParam(name = "searchvalue") String searchvalue) {

        Map<String, Object> retMap = new HashMap<>();

        try {

            // 1. product.html에서 storageno 받아오기
            log.info("purchaselistbysearch.json => {}, {}, {}", receivestate, searchoption, searchvalue);

            List<PurchaseEntity> purchaseList = new ArrayList<>();
            
            if (searchoption.equals("purchaseno")) {
                // 2. deliveryno=3(배달 완료), purchaseno = searchvalue, receivestate=?인 PURCHASE의
                // 번호, 참여자수 가져오기
                purchaseList = adminService.findPurchaseByPurchaseNoAndDelieveryNo(
                            BigDecimal.valueOf(Long.parseLong(searchvalue)), receivestate);

            } else if (searchoption.equals("memid")) {
                // 2. deliveryno=3(배달 완료), memid = searchvalue, receivestate=?인 PURCHASE의 번호 가져오기

                List<BigDecimal> purchaseNoList = adminService.selectPurchaseNoByMemId(searchvalue);
                
                if(purchaseNoList.size() > 0) {
                    for(BigDecimal obj : purchaseNoList) {
                        PurchaseEntity purchase = test.findById(obj).orElse(null);
                        purchaseList.add(purchase);
                    }
                    log.info("purchaseList check 2222222 => {}", purchaseList);
                }
                
            }

            // 3. purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기
            if (purchaseList.size() > 0) {
                for (PurchaseEntity obj : purchaseList) {
                    log.info("purchaseNo check => {}", obj.getNo());
                    List<KmPurchaseStatusIdProjection> testMemIdList = adminService
                            .findIdByPurchaseNoAndState(obj.getNo());

                    List<String> memIdList = new ArrayList<>();

                    for (KmPurchaseStatusIdProjection obj1 : testMemIdList) {

                        log.info("memId check => {}", obj1.getCustomerEntity().getId());

                        memIdList.add(obj1.getCustomerEntity().getId());
                    }
                    obj.setMemIdList(memIdList);
                    // log.info("abcedfg => {}", obj.toString());
                }
                retMap.put("purchaseList", purchaseList);
                log.info("retMap check => {}", retMap.get("purchaseList"));
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

}
