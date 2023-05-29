package com.example.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class kmPurchaseView implements Serializable {

    private long no; // 공구 현황 번호
    private long purchaseNo;
    private long participant; // 참여인원
    private Date deadline; // 공구 종료시간
    private long remainingPerson; // 잔여인원
    // private long state; // 상태
    // private long cancel; // 취소 상태

    private long itemNo;
    private String itemName;
    private long itemPrice;
    // private String sellerName;

    private long storageNo;
    private String storageName;

    // private long sCategoryCode;
    // private String sCategoryName;
    // private String mCategoryName;
    // private String lCategoryName;

    // 임시변수
    private long pricePerOne; // 인당가격
    private String imageUrl; // 대표이미지
    List<String> memIdList; // 공구에 참가 중인 아이디 리스트
}
