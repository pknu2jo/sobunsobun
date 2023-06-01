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
public class SeSelectItemListView {

    private long itemno;
    private String name;
    private long price;
    private long quantity; 
    private long scode; // 소분류 코드
    private Date regdate; // 물품 등록일
    private long purchasecnt; // 공구가 열린 전체 횟수

    // 임시 변수
    private String search; // 검색어
    private String orderby; // 정렬기준 컬럼
    private String sort; // 정렬방식 DESC ASC
    private int start; // 페이지네이션용
    private int end; // 페이지네이션용
    
}
