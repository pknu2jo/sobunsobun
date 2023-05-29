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
public class Review {
    
    private long no; // pk (리뷰 번호), seq_review_no.nextval
    private long rating; // 평점 (not null, 1 ~ 5 값만 입력되도록 제약 조건 걸려있음)
    private String comment; // 리뷰 내용
    private Date regdate; // 등록일
    private long itemNo; // 물품 번호
    private String orderNo; // 주문 번호 (고객아이디 + 결제시간으로 이루어져있음)

}
