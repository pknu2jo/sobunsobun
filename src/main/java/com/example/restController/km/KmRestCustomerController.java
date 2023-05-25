package com.example.restController.km;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerUser;
import com.example.dto.Purchase;
import com.example.dto.PurchaseOrder;
import com.example.dto.PurchaseStatus;
import com.example.mapper.km.KmCustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class KmRestCustomerController {
    
    // 나중에 service 연결해주기
    final KmCustomerMapper mapper;

    // 결제(찐) 성공하면 오는 컨트롤러
        // 여기서 공구 번호를 확인해서  (번호=0 => 공구 개설, 번호!=0 => 공구 참여)
        // 공구 참여면 주문, 공구현황만 추가해주고,
        // 공구 개설이면 공구, 주문, 공구현황 3개 추가 
        // 물품 수량도 -1해주기
    @PostMapping(value="/order.json")
    public Map<String, Object> insertPurchasePOST(@RequestBody PurchaseOrder purchaseOrder, 
                                            @AuthenticationPrincipal CustomerUser user) {
        
        Map<String, Object> retMap = new HashMap<>();
        
        try {
            log.info("rest 확인 => {}", purchaseOrder.toString());
            // PurchaseOrder(no=se4517, memId=null, purchaseNo=0, regdate=null, 
            //             totalPrice=30599, participant=3, storageNo=4, itemNo=11)

            long purchaseNo = purchaseOrder.getPurchaseNo();
            int ret1 = 0;

            // 공구 개설하기 (purchaseOrder.getPurchaseNo == 0) 
            if(purchaseNo == 0) {
                
                // 1. SEQ_PURCHASE_NO.NEXTVAL 가져오기
                purchaseNo = mapper.selectSeqPurchaseNo();

                // 2. purchase 데이터 추가
                    // no(1에서 가져온 값 넣어줌), participant, storageNo
                Purchase purchase = new Purchase();
                purchase.setNo(purchaseNo);
                purchase.setParticipant(purchaseOrder.getParticipant());
                purchase.setStorageNo(purchaseOrder.getStorageNo());
                ret1 = mapper.insertOnePurchase(purchase);      

            }      

            // 여기부터는 개설, 참여 공통 부분

            // 3. purchaseOrder 데이터 추가
                // no, memId, totalPrice, purchaseNo(1에서 가져온 값 넣어줌)
            purchaseOrder.setMemId(user.getUsername());
            purchaseOrder.setPurchaseNo(purchaseNo);            
            int ret2= mapper.insertOnePurchaseOrder(purchaseOrder);

            // 4. purchaseStatus 데이터 추가
                // memId, itemNo, purchaseNo(1에서 가져온 값 넣어줌)
            PurchaseStatus purchaseStatus = new PurchaseStatus();
            purchaseStatus.setMemId(user.getUsername());
            purchaseStatus.setItemNo(purchaseOrder.getItemNo());
            purchaseStatus.setPurchaseNo(purchaseNo);
            int ret3 = mapper.insertOnePurchaseStatus(purchaseStatus);

            if(purchaseOrder.getPurchaseNo() == 0) { // 공구 개설
                log.info("공구개설 ret 확인 => {} {} {}",ret1, ret2, ret3);
            } else { // 공구 참여
                log.info("공구참여 ret 확인 => {} {}", ret2, ret3);
            }

            if( purchaseNo == 0 ) {
                if (ret1 == 1 && ret2 == 1 && ret3 == 1) {
                    retMap.put("result", 200);
                }
            } else if( purchaseNo != 0 ) {
                if (ret2 == 1 && ret3 == 1) {
                    retMap.put("result", 200);
                }
            }  
            
            // 공구의 참여 인원 다 찼는지 체크 해주기
            int remaingPerson = mapper.countRemainingPerson(purchaseNo);

            if ( remaingPerson == 0 ) {
                // item의 수량 -1 해주기

                // 공구 참여중인 사람 목록 다 불러오기

                // 공구 현황 일괄 insert
            }


        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }
    
}
