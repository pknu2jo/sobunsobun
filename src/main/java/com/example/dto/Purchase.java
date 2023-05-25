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
public class Purchase {
    
    private long no; // pk => 공구 주문 번호

    private long participant; // 참여 인원 

    private Date regdate; // 공구 등록일시 (첫번째 공구 연 사람이 결제 하고 난 직후)

    private Date deadline; // 마감기한 = 공구 등록일시 + 7일

    private long receivestate; // 물품 수령 상태 (0, 1)

    private long headCount; // 공구 인원수 체크 (보관함에 받은 이후 가져간 사람 체크위해)

    private Date receiveDate; // 보관소 도착일 (배송 완료 날짜)

    private long deliveryNo; // fk => 배송 현황(0 ~ 3)

    private long storageNo; // fk => 보관소 번호

    // 임시변수 
    private String storageName; // 보관소 이름
    private long remaingPerson; // 남은 인원 체크
}
