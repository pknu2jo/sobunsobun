package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ITEM")
@SequenceGenerator(name = "SEQ_ITEM_NO", sequenceName = "SEQ_ITEM_NO", initialValue = 1, allocationSize = 1)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_NO")
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PRICE")
    private BigDecimal price;
    
    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;
    
    @Column(name = "REGNO")
    private String regNo;
    
    @Column(name = "SCATEGORYCODE")
    private BigDecimal scategoryCode;
}
