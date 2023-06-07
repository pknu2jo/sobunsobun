package com.example.dto;

import java.io.Serializable;

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
public class KmOrderSuccess implements Serializable {

    private static final long serialVersionUID = -2653518554546631545L;
    
    private String itemname;
    private String orderno;
    private String totalprice;
    private String storagename;
    private String orderdate;
    private long purchaseno;
    private long itemno;

}
