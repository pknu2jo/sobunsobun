package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "PURCHASESTATUS")
@SequenceGenerator(name = "SEQ_PSTATUS_NO", sequenceName = "SEQ_PSTATUS_NO", initialValue = 1, allocationSize = 1)
public class PurchaseStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PSTATUS_NO")
    @Column(name = "NO")
    private BigDecimal no;

    private BigDecimal state;
    private BigDecimal cancel;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMID", referencedColumnName = "ID")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASENO", referencedColumnName = "NO")
    private PurchaseEntity purchaseEntity;

    @ManyToOne
    @JoinColumn(name = "ITEMNO", referencedColumnName = "NO")
    @ToString.Exclude
    private Item itemEntity;
    

}
