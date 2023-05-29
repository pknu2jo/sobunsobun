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
public class LikeItem {

    // 관심물품번호(시퀀스) - PK
    private String no;
    // 고객아이디 - FK
    private String memId;
    // 소분류코드번호 - FK
    private long scategoryCode;
    // 등록일자
    private Date regdate;
}
