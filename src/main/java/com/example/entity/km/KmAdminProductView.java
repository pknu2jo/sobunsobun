package com.example.entity.km;

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
@Immutable // 뷰일경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "kmadminproductview")
public class KmAdminProductView {

    @Column(name = "purchaseno")
    private BigDecimal purchaseno;

    @Column(name = "participant")
    private BigDecimal participant;

    @Column(name ="headcount")
    private BigDecimal headcount;

    @Column(name = "receivestate")
    private BigDecimal receivestate;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSS")
    @Column(name = "receivedate")
    private Date receivedate;
    
    @Column(name = "storageno")
    private BigDecimal storageno;

    @Column(name = "storagename")
    private String storagename;

    @Column(name = "memid")
    private String memid;

    @Column(name = "itemno")
    private BigDecimal itemno;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "state")
    private BigDecimal state;  
    
    @Id
    @Column(name = "purchasestatusno")
    private BigDecimal purchasestatusno;
}
