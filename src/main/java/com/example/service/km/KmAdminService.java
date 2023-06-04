package com.example.service.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.PurchaseEntity;
import com.example.entity.StorageEntity;
import com.example.entity.km.KmPurchaseStatusIdProjection;

@Service
public interface KmAdminService {
    
// 상품 수령 관리

    // Controller => product.do GET ------------------------------------------
    
        // 모든 보관소 정보 가져오기
        public List<StorageEntity> findAllStorage();

    // RestController => purchaselistbystorage.json ------------------------------------------
    
        // storageno=?와 deliveryno=3(배달 완료), receiveState=?인 공구 리스트 가져오기
        public List<PurchaseEntity> findPurchaseByStoragenoAndDelieveryNo(BigDecimal storageno, BigDecimal receivestate);

        // purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기 
        public List<KmPurchaseStatusIdProjection> findIdByPurchaseNoAndState(BigDecimal purchaseno);

    // RestController => purchaselistbysearch.json ------------------------------------------
        // 2-1. deliveryno=3(배달 완료), purchaseno = ?, receivestate=?인 PURCHASE의 번호, 참여자수 가져오기
        public List<PurchaseEntity> findPurchaseByPurchaseNoAndDelieveryNo(BigDecimal purchaseno, BigDecimal receivestate);

        // 2-2. deliveryno=3(배달 완료), memid = searchvalue, receivestate=?인 PURCHASE의 번호 가져오기
        public List<BigDecimal> selectPurchaseNoByMemId(String memid); 
    }
