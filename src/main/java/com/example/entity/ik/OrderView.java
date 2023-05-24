package com.example.entity.ik;

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
@Table(name = "ORDERVIEW")
public class OrderView {
    
    @Column(name = "NO")
    private String no;
    
    @Column(name = "PNO")
    private BigDecimal pno;

    @Column(name = "ITEMCODE")
    private BigDecimal itemcode;

    @Column(name = "ITEMNAME")
    private String itemname;

    @Column(name = "ADDRESS")
    private String address;

    @Id
    @Column(name = "MEMID")
    private String memid;

    @Column(name = "STATE")
    private BigDecimal state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "REGDATE")
    private Date regdate;
}
