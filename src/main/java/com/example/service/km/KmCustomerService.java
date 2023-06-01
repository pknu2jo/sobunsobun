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
import com.example.entity.JjimEntity;
import com.example.entity.ReviewEntity;
import com.example.entity.ReviewImageEntity;
import com.example.entity.km.KmCheckReviewView;
import com.example.entity.km.KmOrderNoProjection;
import com.example.entity.km.KmReviewNoProjection;


@Service
public interface KmCustomerService {

// 물품 상세 조회 페이지----------------------------------------------------------------------------------------

    // 물품 조회
        // 상품 정보 가져오기
        public Map<String, Object> selectOneItem(long no);

        // 상품 번호에 해당하는 이미지 번호 가져오기
        public List<Long> selectItemImageNoList(long itemno);
        
        // 상품에 대한 열린 공구 가져오기 => 남은인원
        public int countRemainingPerson(long purchaseno);

        // 상품에 대한 열린 공구 가져오기 => 공구번호, 참여인원, 마감기한, 보관소 코드, 보관소이름
        public List<kmPurchaseView> selectPurchaseList(long itemno);

        // 모든 보관소 정보 가져오기
        public List<Storage> selectStorageList();

        // 찜 여부 확인하기
        public int checkJjim(String memid, BigDecimal itemno);

        // 찜 등록
        public void insertJjim(JjimEntity jjim);

        // 찜 해제(삭제)
        public void deleteJjim(JjimEntity jjim);

    // 리뷰 등록
        // 리뷰 등록 전 구매한 상품이 맞는지 확인하기
        public List<BigDecimal> selectCheckOrder(long itemno, String memid);

        // 리뷰 작성 여부 확인하기 (위에서 purchaseNo 받아옴)
        public KmCheckReviewView checkReview(String memid, BigDecimal purchaseno);

        // purchaseNo, memId에 해당하는 주문 번호 가져오기
        public KmOrderNoProjection findByCustomerEntity_idAndPurchaseEntity_no(String memid, BigDecimal purchaseno);

        // 리뷰 전체에서 가장 최신 번호 가져오기
        // public KmReviewNoProjection findTop1ReviewNo();

        // 가장 최신 리뷰 가져오기 (이미지 등록 위해)
        public ReviewEntity findByPurchaseOrderEntity_no(String orderNo);

        // 리뷰 저장하기
        public int saveReview(ReviewEntity obj);

        // 리뷰 이미지 저장하기
        public int saveReviewImage(ReviewImageEntity obj);

    // 리뷰 조회
        // 물품별 리뷰 전체 조회하기 => 최신순(no desc)
        public List<ReviewEntity> findByItemEntity_noOrderByNoDesc(BigDecimal itemNo, int page);
        // public List<ReviewEntity> findByItemEntity_noOrderByNoDesc(BigDecimal itemNo);

        // 물품별 리뷰 전체 조회하기 => 평점순(rating desc, no desc)
        public List<ReviewEntity> findByItemEntity_noOrderByRatingDescNoDesc(BigDecimal itemNo,int page);
        // public List<ReviewEntity> findByItemEntity_noOrderByRatingDescNoDesc(BigDecimal itemNo);

        // 리뷰 번호에 대한 모든 이미지 가져오기
        public List<KmReviewNoProjection> selectReviewImageNoList(BigDecimal reviewNo);

        // 물품별 리뷰 총 개수
        public long countByItemEntity_no(BigDecimal itemNo);

// 결제 페이지 => /customer/item/order.do ------------------------------------------------------------------------------

    // 공구 정보 한개 가져오기
    public kmPurchaseView selectOnePurchase(long purchaseNo);

    // 보관소 번호에 해당하는 보관소 정보 가져오기
    public Storage selectOneStorage(long storageNo);

    // 고객 정보 가져오기(id, name, phone, email) 
    public Customer selectOneCustomer(String id);

// 결제 페이지 => /api/customer/order.json -----------------------------------------------------------------------------------

    // SEQ_PURCHASE_NO.NEXTVAL 가져오기
    public long selectSeqPurchaseNo();

    // purchaseOrder 데이터 추가
    public int insertOnePurchaseOrder(PurchaseOrder purchaseOrder);

    // purchase 데이터 추가
    public int insertOnePurchase(Purchase purchase);

    // purchaseStatus 데이터 추가
    public int insertOnePurchaseStatus(PurchaseStatus purchaseStatus);

    // 공구에 참여 중인 memId 가져오기
    public List<String> selectIdList(long purchaseNo);

    // 공구 인원 다 참 -> purchastStatus 일괄 insert
    public int PurchaseStatusInsertBatch(List<PurchaseStatus> obj);
    
    // item 수량 -1 해주기
    public int updateItemQuantity(long itemNo);
    
// 이미지 -----------------------------------------------------------------------------------------

    // 물품 이미지 번호에 대한 정보 가져오기
    public ItemImage findItemImageById(BigDecimal no);

    // itemno에 해당하는 모든 이미지 가져오기
    public List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no);

    // 리뷰 이미지 번호에 대한 정보 가져오기
    public ReviewImageEntity findReviewImageById(BigDecimal reviewImageNo);


// ----------------------------------------------------------------------------------------------------

}
