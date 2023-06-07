package com.example.dto;

import java.math.BigDecimal;

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
public class KmAdminPurchaseStatusDTO {
    
    // BigDecimal purchaseno;
    String memid;
    BigDecimal state;
}
