package com.example.entity.se;

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
import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable // 뷰일경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "SECHKPURCHASEDEADLINEVIEW")
@SequenceGenerator(name = "SEQ_PURCHASE_NO", sequenceName = "SEQ_PURCHASE_NO", initialValue = 1, allocationSize = 1)
public class SeChkPurchaseDeadlineView {

    @Id
    @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PURCHASE_NO")
    private BigDecimal no; // purchasestatus's no(pk)

    @Column(name = "memid")
    private String memid;
    
    @Column(name = "purchaseno")
    private BigDecimal purchaseno;

    @Column(name = "itemno")
    private BigDecimal itemno;


    @CreationTimestamp
    @Column(name = "deadline", insertable = true, updatable = false)
    private Date deadline;

    
}
