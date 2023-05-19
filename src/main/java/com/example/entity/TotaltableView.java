package com.example.entity;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Immutable // 뷰
@Entity
@Table(name = "TOTALTABLE")
public class TotaltableView {
    
    @Column(name = "NO")
    private String no;

    @Id
    @Column(name = "ITEMNO")
    private BigDecimal itemno;
    
    @Column(name = "ITEMNAME")
    private String itemname;

    @Column(name = "ITEMPRICE")
    private BigDecimal itemprice;

    @Column(name = "ITEMQUANTITY")
    private BigDecimal itemquantity;

    @Column(name = "ITEMSCATEGORY")
    private BigDecimal itemscategory;

    @Column(name = "COUNT")
    private long count;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "ITEMREGDATE")
    private Date itemregdate;
}
