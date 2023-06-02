package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "PURCHASEORDER")
public class PurchaseOrderEntity {

    @Id
    @Column(name = "NO")
    private String no;

    @Column(name = "TOTALPRICE")
    private BigDecimal totalPrice;

    @Column(name = "STATE")
    private BigDecimal state = BigDecimal.valueOf(0L);

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASENO", referencedColumnName = "NO")
    private PurchaseEntity purchaseEntity;

    @OneToOne(mappedBy = "purchaseOrderEntity")
    private ReviewEntity reviewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMID", referencedColumnName = "ID")
    private CustomerEntity customerEntity;
}
