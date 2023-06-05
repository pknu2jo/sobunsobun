
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

@Entity
@Data
@Immutable
@Table(name = "GRGRPURCHASEVIEW")
public class grgrpurchaseview {

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

    @Column(name = "STATE")
    private BigDecimal state;

    @Column(name = "CANCEL")
    private BigDecimal cancel;

    @Column(name = "ITEMNO")
    private BigDecimal itemno;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARTICIPANT")
    private BigDecimal participant;

    @Column(name = "SNAME")
    private String sname;

    @Transient
    private long commaprice;

    @Transient
    private String statechk;

}
