package com.example.service.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.PurchaseEntity;
import com.example.entity.PurchaseStatusEntity;
import com.example.entity.StorageEntity;
import com.example.entity.km.KmAdminProductSimpleView;
import com.example.repository.km.KmAdminProductSimpleViewRepository;
import com.example.repository.km.KmAdminProductViewRepository;
import com.example.repository.km.KmPurchaseRepository;
import com.example.repository.km.KmPurchaseStatusRepository;
import com.example.repository.km.KmPurchaseStatusRepository.KmAdminPurchaseStatus;
import com.example.repository.km.KmStorageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmAdminServiceImpl implements KmAdminService {
    
    final KmStorageRepository storageR;
    final KmPurchaseRepository purchaseR;
    final KmAdminProductViewRepository productR;
    final KmAdminProductSimpleViewRepository productSimpleR;
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

    // 모든 지점 선택 시, 수령 상태에 따른 공구 주문 가져오기
    @Override
    public List<KmAdminProductSimpleView> findPurchaseByReceivestate(BigDecimal receivestate) {
        try {
            return productSimpleR.findByReceivestate(receivestate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 지점(storageno)별, 수령 상태에 따른 공구 주문 가져오기
    @Override
    public List<KmAdminProductSimpleView> findPurchaseByStoragenoAndReceivestate(BigDecimal storageno, BigDecimal receivestate) {
        try {
            return productSimpleR.findByStoragenoAndReceivestate(storageno, receivestate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // purchaseno=?와 state=1인 PURCHASESTATUS의 memid들 가져오기 
    @Override
    public List<KmAdminPurchaseStatus> findMemidAndStateByPurchaseno(BigDecimal purchaseno) {
        try {
            return purchaseStatusR.findMemidAndStateByPurchaseno(purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 // RestController => purchaselistbysearch.json ------------------------------------------
        // 2-1. deliveryno=3(배달 완료), purchaseno = ?, receivestate=?인 PURCHASE의 번호, 참여자수 가져오기
        @Override
        public List<PurchaseEntity> findPurchaseByPurchaseNoAndDelieveryNo(BigDecimal purchaseno, BigDecimal receivestate) {
            try {
                return purchaseR.findByNoAndDeliveryEntity_noAndReceiveStateOrderByNoAsc(purchaseno, BigDecimal.valueOf(3), receivestate);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // 2-2. deliveryno=3(배달 완료), memid = searchvalue, receivestate=?인 PURCHASE의 번호 가져오기
        @Override
        public List<BigDecimal> selectPurchaseNoByMemId(String memid) {
            try {
                return purchaseR.selectPurchaseNoByMemId(memid);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    // RestController => insertstatus2.json ------------------------------------------
        // state=2로 insert 해주기
        @Override
        public int insertPurchaseStatus(PurchaseStatusEntity purchaseStatus) {
            try {
                purchaseStatusR.save(purchaseStatus);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        // 공구 번호로 공구 한개 가져오기
        @Override
        public PurchaseEntity findOnePurchase(BigDecimal purchaseno) {
            try {
                return purchaseR.findById(purchaseno).orElse(null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // 공구 업데이트 해주기 (headcount += 1)
        @Override
        public int updatePurchaseHeadcount(PurchaseEntity purchase){
            try {
                purchaseR.save(purchase);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
}
