package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"pw"})
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    // 관리자 아이디 - PK
    private String id;
    // 비밀번호
    private String pw;
}
