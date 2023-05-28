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




@Service
public interface KmCustomerService {


// 물품 상세 조회 페이지

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

// ----------------------------------------------------------------------------------------------------
// 결제 페이지 => /customer/item/order.do 

    // 공구 정보 한개 가져오기
    public kmPurchaseView selectOnePurchase(long purchaseNo);

    // 보관소 번호에 해당하는 보관소 정보 가져오기
    public Storage selectOneStorage(long storageNo);

    // 고객 정보 가져오기(id, name, phone, email) 
    public Customer selectOneCustomer(String id);

// ----------------------------------------------------------------------------------------------------
// 결제 페이지 => /api/customer/order.json 

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
    

// 이미지
    // 이미지 번호에 대한 정보 다 가져오기
    public ItemImage findById(BigDecimal no);

    // itemno에 해당하는 모든 이미지 가져오기
    public List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no);

    // itemno에 해당하는 이미지 중 가장 오래된 이미지 가져오기
    // public ItemImage findTop1ByItemNo_noOrderByNoAsc(BigDecimal no);


// ----------------------------------------------------------------------------------------------------
// 리뷰 등록
    
    // 리뷰 등록 전 구매한 상품이 맞는지 확인하기
    public List<BigDecimal> selectCheckOrder(String itemno, String memid);

    // 리뷰 작성 여부 확인하기 (위에서 purchaseNo 받아옴)
    public long countCheckReview(String memid, long purchaseno);
}
