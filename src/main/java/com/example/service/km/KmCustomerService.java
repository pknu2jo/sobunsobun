package com.example.service.km;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
// 결제 페이지

    // 공구 정보 한개 가져오기
    public kmPurchaseView selectOnePurchase(long purchaseNo);

    // 보관소 번호에 해당하는 보관소 정보 가져오기
    public String selectOneStorage(long storageNo);

// 이미지
    // 이미지 번호에 대한 정보 다 가져오기
    public ItemImage findById(BigDecimal no);

    // itemno에 해당하는 모든 이미지 가져오기
    public List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no);

    // itemno에 해당하는 이미지 중 가장 오래된 이미지 가져오기
    // public ItemImage findTop1ByItemNo_noOrderByNoAsc(BigDecimal no);

}
