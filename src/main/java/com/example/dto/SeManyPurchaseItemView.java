package com.example.dto;

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
public class SeManyPurchaseItemView {

    private long itemno; // 물품번호
    private String name; // 물품명
    private long price; // 물품가격
    private long purchasecnt; // 공구가 일어난 횟수
    
}
