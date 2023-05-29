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
public class Customer {
    // 고객아이디 - PK
    private String id;
    // 비밀번호
    private String pw;
    // 이름
    private String name;
    // 연락처
    private String phone;
    // 이메일
    private String email;
    // 닉네임
    private String nickname;
    // 성별
    private String gender;
    // 등록날짜
    private Date regdate;
    // 블랙리스트체크(기본값 0, 블랙리스트가 되면 1)
    private long blockChk;
    // 탈퇴유뮤(기본값 0, 탈퇴하면 1)
    private long quitChk;

    private String newPw;
    private int statechk;
}
