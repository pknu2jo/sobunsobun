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
public class SeDeadlinePurchaseDdayView {

    private long purchaseno;
    private Date deadline;
    private long itemno;
    private String name;
    private long price;
    private long dday;
    
}
