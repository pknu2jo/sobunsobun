package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "pw" })
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    
    // 사업자등록번호(0으로 시작하는 사업자번호가 있을수 있으니 String으로!) - PK
    private String no;
    // 비밀번호
    private String pw;
    // 업체명
    private String name;
    // 연락처
    private String phone;
    // 이메일
    private String email;
    // 주소
    private String address;
    // 등록날짜
    private Date regdate;
    // 블랙리스트체크(기본값 0, 블랙리스트가 되면 1)
    private long blockChk;

    private String newPw;
}
