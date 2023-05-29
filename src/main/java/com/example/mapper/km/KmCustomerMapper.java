package com.example.mapper.km;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Customer;
import com.example.dto.Purchase;
import com.example.dto.PurchaseOrder;
import com.example.dto.PurchaseStatus;
import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;

@Mapper
public interface KmCustomerMapper {

// 물품 상세 조회 페이지

    // 상품 정보 가져오기
    public Map<String, Object> selectOneItem(long no);

    // 상품 번호에 해당하는 이미지 번호 가져오기
    public List<Long> selectItemImageNoList(long itemno);

    // 상품에 대한 열린 공구 가져오기 -> 남은 인원
    public int countRemainingPerson(long purchaseno);

    // 상품에 대한 열린 공구 가져오기 => 공구번호, 참여인원, 마감기한, 보관소 코드, 보관소이름
    public List<kmPurchaseView> selectPurchaseList(long itemno);
        
    // 모든 보관소 정보 가져오기
    public List<Storage> selectStorageList();


// 결제 페이지 => /customer/item/order.do

    // 공구 정보 한개 가져오기
    public kmPurchaseView selectOnePurchase(long purchaseNo);

    // 보관소 번호에 해당하는 보관소 정보 가져오기
    public Storage selectOneStorage(long storageNo);
    
    // 고객 정보 가져오기(id, name, phone, email)
    public Customer selectOneCustomer(String id);


// 결제페이지 => /api/customer/order.json

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

}
