package com.example.entity.gr;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Immutable
@Table(name = "GRLIKEITEMVIEW")
public class grlikeitemview {

    @Id
    @Column(name = "JJIMNO")
    private BigDecimal jjimno;

    @Column(name = "MEMID")
    private String memid;

    @Column(name = "ITEMNO")
    private BigDecimal itemno;

    @Column(name = "REGDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date regdate;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

}
