package com.example.entity.ikh;

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
@Table(name = "DELIVERYVIEW")
public class DeliveryView {
    
    @Column(name = "NO")
    private String no;
    
    @Column(name = "ITEMCODE")
    private BigDecimal itemcode;

    @Column(name = "ITEMNAME")
    private String itemname;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PURCHASENO")
    private BigDecimal purchaseno;

    @Column(name = "DELIVERY")
    private BigDecimal delivery;

    @Id
    @Column(name = "ENID")
    private BigDecimal enid;

    @Column(name = "LNAME")
    private String lname;

    @Column(name = "MNAME")
    private String mname;

    @Column(name = "SNAME")
    private String sname;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "REGDATE")
    private Date regdate;
}
