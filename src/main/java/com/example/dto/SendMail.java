package com.example.dto;

import lombok.Data;

@Data
public class SendMail {
    
    private String address;
    private String title;
    private String message;

}
