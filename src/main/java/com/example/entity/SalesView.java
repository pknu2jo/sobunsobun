package com.example.entity;

import java.math.BigDecimal;
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
@Table(name = "SALES")
public class SalesView {
    @Id
    @Column(name = "NO")
    private String no;
    
    @Column(name = "ITEMNO")
    private BigDecimal itemno;

    @Column(name = "ITEMPRICE")
    private BigDecimal itemprice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "REGDATE")
    private Date regdate;
}
