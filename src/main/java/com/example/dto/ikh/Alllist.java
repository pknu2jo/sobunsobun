package com.example.dto.ikh;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Alllist {
    String no;
    BigDecimal pno;
    BigDecimal itemcode;
    String itemname;
    String address;
    String memid;
    BigDecimal state;
    BigDecimal enid;
    BigDecimal count;
    BigDecimal participant;
    Date regdate;    
}
