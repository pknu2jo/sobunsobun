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
public class Storage {
    
    private long no; // 보관소 번호(pk)
    private String name; // 보관소명
    private String phone; // 전화번호

    private String phostcode; // 우편번호
    private String address1; // 주소
    private String address2; // 상세 주소
    private String address3; // 참고항목

    private double latitude; // 위도
    private double longitude; // 경도

    private String adminId; // 관리자 아이디

    private Date regdate; // 등록일자
}
