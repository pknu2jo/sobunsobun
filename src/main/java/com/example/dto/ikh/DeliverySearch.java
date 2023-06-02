package com.example.dto.ikh;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DeliverySearch {
    BigDecimal itemcode=null;
    String itemname="";
    String address="";
    BigDecimal purchaseno=null;
    BigDecimal status=null;
    String firstdate="";
    String seconddate="";
}
