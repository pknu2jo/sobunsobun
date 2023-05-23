package com.example.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailMessage { 
    // [Seller용]
    // 전송할 메일의 정보를 저장

    private String address;
    private String title;
    private String message;
}