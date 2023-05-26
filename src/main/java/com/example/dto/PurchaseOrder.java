package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PurchaseOrder {
    
    private String no; // 주문 번호
    private String memId; // 회원 아이디
    private long purchaseNo; // 공구 번호
    private Date regdate; // 주문시간
    private long totalPrice; // 총 주문 금액

    // 임시변수
    private long participant;
    private long storageNo;
    private long itemNo;
}
