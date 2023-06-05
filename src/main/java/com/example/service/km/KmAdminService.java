package com.example.service.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.PurchaseEntity;
import com.example.entity.PurchaseStatusEntity;
import com.example.entity.StorageEntity;
import com.example.entity.km.KmAdminProductSimpleView;
import com.example.repository.km.KmPurchaseStatusRepository.KmAdminPurchaseStatus;

@Service
public interface KmAdminService {
    
// 상품 수령 관리

    // Controller => product.do GET ------------------------------------------
    
        // 모든 보관소 정보 가져오기
        public List<StorageEntity> findAllStorage();

    // RestController => purchaselistbystorage.json ------------------------------------------
    
        // 모든 지점 선택 시, 수령 상태에 따른 공구 주문 가져오기
        public List<KmAdminProductSimpleView> findPurchaseByReceivestate(BigDecimal receivestate);

        // 지점(storageno)별, 수령 상태에 따른 공구 주문 가져오기
        public List<KmAdminProductSimpleView> findPurchaseByStoragenoAndReceivestate(BigDecimal storageno, BigDecimal receivestate);

        // purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기 
        public List<KmAdminPurchaseStatus> findMemidAndStateByPurchaseno(BigDecimal purchaseno);

    // RestController => purchaselistbysearch.json ------------------------------------------
        // 2-1. deliveryno=3(배달 완료), purchaseno = ?, receivestate=?인 PURCHASE의 번호, 참여자수 가져오기
        public List<PurchaseEntity> findPurchaseByPurchaseNoAndDelieveryNo(BigDecimal purchaseno, BigDecimal receivestate);

        // 2-2. deliveryno=3(배달 완료), memid = searchvalue, receivestate=?인 PURCHASE의 번호 가져오기
        public List<BigDecimal> selectPurchaseNoByMemId(String memid); 

    // RestController => insertstatus2.json ------------------------------------------
        // state=2로 insert 해주기
        public int insertPurchaseStatus(PurchaseStatusEntity purchaseStatus);

        // 공구 번호로 공구 한개 가져오기
        public PurchaseEntity findOnePurchase(BigDecimal purchaseno);

        // 공구 업데이트 해주기 (headcount += 1)
        public int updatePurchaseHeadcount(PurchaseEntity purchase);
    }
