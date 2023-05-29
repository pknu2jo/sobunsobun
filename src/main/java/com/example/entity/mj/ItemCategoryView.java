package com.example.entity.mj;

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
@Immutable // 조회만 가능한 엔티티
@Data
@Table(name = "ITEMCATEGORYVIEW")
public class ItemCategoryView {
    
    @Id
    @Column(name="NO")
    private BigDecimal no;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "REGDATE")
    private Date regdate;

    @Column(name = "REGNO")
    private String regNo;

    @Column(name = "SCATEGORYCODE")
    private BigDecimal scategoryCode;

    @Column(name = "SNAME")
    private String sname;

    @Column(name = "MCATEGORYCODE")
    private BigDecimal mcategoryCode;

    @Column(name = "MNAME")
    private String mname;
    
    @Column(name = "LCATEGORYCODE")
    private BigDecimal lcategoryCode;

    @Column(name = "LNAME")
    private String lname;
}
