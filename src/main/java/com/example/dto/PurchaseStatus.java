package com.example.dto;

import java.util.Date;

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
public class PurchaseStatus {
    
    private long no;            // pk, 공구 현황 (seq_pstatus_no.nextval)
    private long state;         // 공구 상태 (0 : 정상, 1 : 모집완료)
    private long cancel;        // 취소 여부 (0 : not 취소, 1 : 취소)
    private Date regdate;       // 등록일자
    private String memId;       // 고객 아이디
    private long purchaseNo;    // 공구주문번호
    private long itemNo;        // 물품 번호
}
