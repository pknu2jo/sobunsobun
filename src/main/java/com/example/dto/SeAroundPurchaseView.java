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
public class SeAroundPurchaseView {

    private long itemno;
    private String name;
    private long price;
    private long purchaseno;
    private long state;
    private Date statusregdate;
    private long storageno;
    private double latitude;
    private double longitude;
    
}
