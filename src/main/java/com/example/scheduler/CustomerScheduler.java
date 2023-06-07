package com.example.scheduler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.CustomerEntity;
import com.example.entity.Item;
import com.example.entity.PurchaseEntity;
import com.example.entity.PurchaseStatusEntity;
import com.example.entity.se.SeChkPurchaseDeadlineView;
import com.example.service.se.SeCustomerService;
import com.example.service.se.SePurchaseItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerScheduler {

    final SeCustomerService cService;
    final SePurchaseItemService purchaseItemService;
    
    // 한달 지난 알림 삭제
    @Scheduled(cron = "0 0 0 1 * *") // 매달 1일 00시
    public void deleteNoti() {
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate oneMonthAgo = currentDate.minusMonths(1);
            Date oneMonthAgoDate = Date.from(oneMonthAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
    
            int ret = cService.deleteByRegdateBefore(oneMonthAgoDate);
            log.info("한달 지난 알림 삭제 => {} 개", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 마감일이 지난 공구의 상태를 cancel = 1 로 insert
    @Scheduled(cron = "0 0 1 * * *") 
    public void chkDeadline() {
        List<SeChkPurchaseDeadlineView> list = purchaseItemService.findAllAfterDeadline();
        log.info("마감지난 공구 => {}", list.toString());

        List<PurchaseStatusEntity> saveList = new ArrayList<>();
        
        for(SeChkPurchaseDeadlineView obj : list) {
            PurchaseStatusEntity purchaseStatusEntity = new PurchaseStatusEntity();
            purchaseStatusEntity.setState(BigDecimal.valueOf(0L));
            purchaseStatusEntity.setCancel(BigDecimal.valueOf(1L));

            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setId(obj.getMemid());
            purchaseStatusEntity.setCustomerEntity(customerEntity);

            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setNo(obj.getPurchaseno());
            purchaseStatusEntity.setPurchaseEntity(purchaseEntity);

            Item itemEntity = new Item();
            itemEntity.setNo(obj.getItemno());
            purchaseStatusEntity.setItemEntity(itemEntity);

            saveList.add(purchaseStatusEntity);
            
        }
        // log.info("확인용 => {}", saveList.toString());
        List<PurchaseStatusEntity> ret = purchaseItemService.saveAllPurchaseStatus(saveList);
        if(ret.size() == saveList.size()) {
            log.info("마감된 공구 취소처리 => {} 개", ret.size());
        }

    }


}
