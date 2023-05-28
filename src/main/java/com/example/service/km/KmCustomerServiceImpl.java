package com.example.service.km;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.Customer;
import com.example.dto.Purchase;
import com.example.dto.PurchaseOrder;
import com.example.dto.PurchaseStatus;
import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;
import com.example.entity.ItemImage;
import com.example.mapper.km.KmCustomerMapper;
import com.example.repository.km.KmPurchaseStatusRepository;
import com.example.repository.km.KmReviewAndOrderViewRepository;
import com.example.repository.km.kmItemImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmCustomerServiceImpl implements KmCustomerService {

    final KmCustomerMapper cMapper;
    final kmItemImageRepository imageRepository;
    final KmPurchaseStatusRepository psRepository;
    final KmReviewAndOrderViewRepository roRepository;


// 물품 상세 조회 페이지

    // 상품 정보 가져오기
    @Override
    public Map<String, Object> selectOneItem(long no) {
        try {
            return cMapper.selectOneItem(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 상품 번호에 해당하는 이미지 번호 가져오기
    @Override
    public List<Long> selectItemImageNoList(long itemno) {
        try {
            return cMapper.selectItemImageNoList(itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 상품에 대한 열린 공구 가져오기 => 남은인원
    @Override
    public int countRemainingPerson(long purchaseno) {
        try {
            return cMapper.countRemainingPerson(purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

     // 상품에 대한 열린 공구 가져오기 => 공구번호, 참여인원, 마감기한, 보관소 코드, 보관소이름
    @Override
    public List<kmPurchaseView> selectPurchaseList(long itemno) {
        try {
            return cMapper.selectPurchaseList(itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 모든 보관소 정보 가져오기
    @Override
    public List<Storage> selectStorageList() {
        try {
            return cMapper.selectStorageList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

// 결제 페이지 => /customer/item/order.do 
    // 공구 정보 한개 가져오기
    @Override
    public kmPurchaseView selectOnePurchase(long purchaseNo) {
        try {
            return cMapper.selectOnePurchase(purchaseNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 보관소 번호에 해당하는 보관소 정보 가져오기
    @Override
    public Storage selectOneStorage(long storageNo) {
        try {
            return cMapper.selectOneStorage(storageNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 고객 정보 가져오기(id, name, phone, email) SELECT id, name, phone, email FROM CUSTOMER c WHERE id='km1'
    @Override
    public Customer selectOneCustomer(String id) {
        try {
            return cMapper.selectOneCustomer(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


// 결제 페이지 => /api/customer/order.json 

    // SEQ_PURCHASE_NO.NEXTVAL 가져오기
    @Override
    public long selectSeqPurchaseNo() {
        try {
            return cMapper.selectSeqPurchaseNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    // purchaseOrder 데이터 추가
    @Override
    public int insertOnePurchaseOrder(PurchaseOrder purchaseOrder) {
        try {
            return cMapper.insertOnePurchaseOrder(purchaseOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // purchase 데이터 추가
    @Override
    public int insertOnePurchase(Purchase purchase) {
        try {
            return cMapper.insertOnePurchase(purchase);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // purchaseStatus 데이터 추가
    @Override
    public int insertOnePurchaseStatus(PurchaseStatus purchaseStatus) {
        try {
            return cMapper.insertOnePurchaseStatus(purchaseStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 공구에 참여 중인 memId 가져오기
    @Override
    public List<String> selectIdList(long purchaseNo) {
        try {
            return cMapper.selectIdList(purchaseNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 공구 인원 다 참 -> purchastStatus 일괄 insert
    @Override
    public int PurchaseStatusInsertBatch(List<PurchaseStatus> obj) {
        try {
            return cMapper.PurchaseStatusInsertBatch(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // item 수량 -1 해주기
    @Override
    public int updateItemQuantity(long itemNo) {
        try {
            return cMapper.updateItemQuantity(itemNo);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

// 이미지
    // 이미지 번호에 대한 정보 다 가져오기
    @Override
    public ItemImage findById(BigDecimal no) {
        try {
            return imageRepository.findById(no).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    // itemno에 해당하는 모든 이미지 가져오기
    @Override
    public List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no) {
        try {
            return imageRepository.findByItemNo_noOrderByNoAsc(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    // itemno에 해당하는 이미지 중 가장 오래된 이미지 가져오기
    // public ItemImage findTop1ByItemNo_noOrderByNoAsc(BigDecimal no) {
    //     try {
    //         return imageRepository.findTop1ByItemNo_noOrderByNoAsc(no);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

// ----------------------------------------------------------------------------------------------------
// 리뷰 등록
    
    // 리뷰 등록 전 구매한 상품이 맞는지 확인하기
    public List<BigDecimal> selectCheckOrder(String itemno, String memid) {
        try {
            return psRepository.selectCheckOrder(itemno, memid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 리뷰 작성 여부 확인하기 (위에서 purchaseNo 받아옴)
    public long countCheckReview(String memid, long purchaseno) {
        try {
            // 작성 했으면 1, 아직 작성 안했으면 0 출력됨
            return roRepository.countByMemidAndPurchaseno(memid, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
