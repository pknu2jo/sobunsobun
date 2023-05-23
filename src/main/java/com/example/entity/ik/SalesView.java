package com.example.entity.ik;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

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
    
    @Column(name = "REGDATE")
    private String regdate;
}
