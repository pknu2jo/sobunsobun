package com.example.service.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.PurchaseEntity;
import com.example.entity.StorageEntity;
import com.example.entity.km.KmPurchaseStatusIdProjection;
import com.example.repository.km.KmPurchaseRepository;
import com.example.repository.km.KmPurchaseStatusRepository;
import com.example.repository.km.KmStorageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmAdminServiceImpl implements KmAdminService {
    
    final KmStorageRepository storageR;
    final KmPurchaseRepository purchaseR;
    final KmPurchaseStatusRepository purchaseStatusR;


// 상품 수령 관리

    // Controller => product.do GET ------------------------------------------
    
    // 모든 보관소 정보 가져오기
    @Override
    public List<StorageEntity> findAllStorage() {
        try {
            return storageR.findAllByOrderByNameAsc();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // RestController => purchaselistbystorage.json ------------------------------------------

    // storageno=?와 deliveryno=3(배달 완료), receiveState=?인 공구 리스트 가져오기
    @Override
    public List<PurchaseEntity> findPurchaseByStoragenoAndDelieveryNo(BigDecimal storageno, BigDecimal receivestate) {
        try {
            return purchaseR.findByStorageEntity_noAndDeliveryEntity_noAndReceiveStateOrderByNoAsc(storageno, BigDecimal.valueOf(3), receivestate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기 
    @Override
    public List<KmPurchaseStatusIdProjection> findIdByPurchaseNoAndState(BigDecimal purchaseno) {
        try {
            return purchaseStatusR.findByPurchaseEntity_noAndStateOrderByCustomerEntity_idAsc(purchaseno, BigDecimal.valueOf(1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
