package com.example.service.km;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.dto.Customer;
import com.example.dto.Purchase;
import com.example.dto.PurchaseOrder;
import com.example.dto.PurchaseStatus;
import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;
import com.example.entity.ItemImage;
import com.example.entity.JjimEntity;
import com.example.entity.ReviewEntity;
import com.example.entity.ReviewImageEntity;
import com.example.entity.km.KmCheckReviewView;
import com.example.entity.km.KmOrderNoProjection;
import com.example.entity.km.KmReviewNoProjection;
import com.example.mapper.km.KmCustomerMapper;
import com.example.repository.km.KmCheckReviewViewRepository;
import com.example.repository.km.KmJjimRepository;
import com.example.repository.km.KmPurchaseOrderRepository;
import com.example.repository.km.KmPurchaseStatusRepository;
import com.example.repository.km.KmReviewImageRepository;
import com.example.repository.km.KmReviewRepository;
import com.example.repository.km.kmItemImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmCustomerServiceImpl implements KmCustomerService {

    final KmCustomerMapper cMapper;
    final kmItemImageRepository iiRepository;
    final KmPurchaseStatusRepository psRepository;
    final KmCheckReviewViewRepository roRepository;
    final KmReviewRepository rRepository;
    final KmReviewImageRepository riRepository;
    final KmPurchaseOrderRepository poRepository;
    final KmJjimRepository jRepository;

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

    // 찜 여부 확인하기
    public int checkJjim(String memid, BigDecimal itemno) {
        try {
            // 찜 여부 확인하기
            return jRepository.countByCustomerEntity_idAndItemEntity_no(memid, itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

     // 찜 등록
     public int insertJjim(JjimEntity jjim) {
        try {
            jRepository.save(jjim);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
     }

     // 찜 해제(삭제)
     public int deleteJjim(String memid, BigDecimal itemno) {
        try {
            jRepository.deleteByCustomerEntity_idAndItemEntity_no(memid, itemno);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
    // 물품 이미지 번호에 대한 정보 다 가져오기
    @Override
    public ItemImage findItemImageById(BigDecimal no) {
        try {
            return iiRepository.findById(no).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    // itemno에 해당하는 모든 이미지 가져오기
    @Override
    public List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no) {
        try {
            return iiRepository.findByItemNo_noOrderByNoAsc(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 리뷰 이미지 번호에 대한 정보 가져오기
    @Override
    public ReviewImageEntity findReviewImageById(BigDecimal reviewImageNo) {
        try {
            return riRepository.findById(reviewImageNo).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };


// ----------------------------------------------------------------------------------------------------
// 리뷰 등록
    
    // 리뷰 등록 전 구매한 상품이 맞는지 확인하기
    public List<BigDecimal> selectCheckOrder(long itemno, String memid) {
        try {
            return psRepository.selectCheckOrder(itemno, memid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 리뷰 작성 여부 확인하기 (위에서 purchaseNo 받아옴)
    public KmCheckReviewView checkReview(String memid, BigDecimal purchaseno) {
        try {
            return roRepository.findByMemidAndPurchaseno(memid, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // purchaseNo, memId에 해당하는 주문 번호 가져오기
    public KmOrderNoProjection findByCustomerEntity_idAndPurchaseEntity_no(String memid, BigDecimal purchaseno) {
        try {
            return poRepository.findByCustomerEntity_idAndPurchaseEntity_no(memid, purchaseno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 가장 최신 리뷰 가져오기 (이미지 등록 위해)
    @Override
    public ReviewEntity findByPurchaseOrderEntity_no(String orderNo) {
        try {
            return rRepository.findByPurchaseOrderEntity_no(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 리뷰 저장하기
    public int saveReview(ReviewEntity obj) {
        try {
            rRepository.save(obj);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 리뷰 이미지 저장하기
    public int saveReviewImage(ReviewImageEntity obj) {
        try {
            riRepository.save(obj);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 물품에 해당하는 리뷰 전체 불러오기 => 최신순(no desc)
    @Value("${review.pagetotal}") int PAGETOTAL;
    @Override
    public List<ReviewEntity> findByItemEntity_noOrderByNoDesc(BigDecimal itemNo, int page) {
        try {
            PageRequest PageRequest = org.springframework.data.domain.PageRequest.of((page-1), PAGETOTAL);

            return rRepository.findByItemEntity_noOrderByNoDesc(itemNo, PageRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // @Override
    // public List<ReviewEntity> findByItemEntity_noOrderByNoDesc(BigDecimal itemNo) {
    //     try {
    //         return rRepository.findByItemEntity_noOrderByNoDesc(itemNo);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
   

    // 물품에 해당하는 리뷰 전체 불러오기 => 평점순(rating desc, no desc)
    @Override
    public List<ReviewEntity> findByItemEntity_noOrderByRatingDescNoDesc(BigDecimal itemNo, int page) {
        try {
            PageRequest PageRequest = org.springframework.data.domain.PageRequest.of((page-1), PAGETOTAL);

            return rRepository.findByItemEntity_noOrderByRatingDescNoDesc(itemNo, PageRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // @Override
    // public List<ReviewEntity> findByItemEntity_noOrderByRatingDescNoDesc(BigDecimal itemNo) {
    //     try {
    //         return rRepository.findByItemEntity_noOrderByRatingDescNoDesc(itemNo);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }



    // 리뷰 번호에 해당하는 리뷰 이미지 번호 가져오기
    public List<KmReviewNoProjection> selectReviewImageNoList(BigDecimal reviewNo) {
        try {
            return riRepository.findByReviewno_noOrderByNoAsc(reviewNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 물품별 리뷰 총 개수
    public long countByItemEntity_no(BigDecimal itemNo) {
        try {
            return rRepository.countByItemEntity_no(itemNo);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
