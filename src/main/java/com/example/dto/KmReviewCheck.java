package com.example.dto;

import lombok.Data;

@Data
public class KmReviewCheck {
    
    private long itemNo; // 물품 번호
    private long purchaseNo; // 공구 번호
    private long reviewCount; // 리뷰 적었는지 안적었는지 체크 여부 (0이면 안적음, 1이면 적음)
}
