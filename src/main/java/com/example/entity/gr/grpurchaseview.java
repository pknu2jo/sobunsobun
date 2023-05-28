package com.example.entity.gr;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Immutable
@Table(name = "GRPURCHASEVIEW")
public class grpurchaseview {

    @Id
    @Column(name = "PONO")
    private String pono;

    @Column(name = "MEMID")
    private String memid;

    @Column(name = "PURCHASENO")
    private BigDecimal purchaseno;

    @Column(name = "REGDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date regdate;

    @Column(name = "TOTALPRICE")
    private BigDecimal totalprice;

    @Column(name = "POSTATE")
    private BigDecimal postate;

    @Column(name = "PSSTATE")
    private BigDecimal psstate;

    @Column(name = "CANCEL")
    private BigDecimal cancel;

    @Column(name = "ITEMNO")
    private BigDecimal itemno;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "SCATEGORYCODE")
    private BigDecimal scategorycode;

    @Column(name = "PARTICIPANT")
    private BigDecimal participant;

    @Column(name = "STORENAME")
    private String storename;

    @Transient
    private long commaprice;

    @Transient
    private String statechk;

}
