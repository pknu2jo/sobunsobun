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
public class Scategory {

    // 소분류코드번호 - PK
    private long code;
    // 소분류종류(한글)
    private String name;
    // 중분류코드번호 - FK
    private long mcategoryCode;
    // 등록일자
    private Date regdate;

}
