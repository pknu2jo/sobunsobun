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
public class CustomerAddress {

    // 시퀀스 PK
    private long no;
    // 우편번호
    private String postcode;
    // 주소
    private String address1;
    // 상세주소
    private String address2;
    // 참고항목
    private String address3;
    // 고객아이디 - FK
    private String memId;
    // 등록일자
    private Date regdate;

    // 위도
    private double latitude;
    // 경도
    private double longitude;
}
