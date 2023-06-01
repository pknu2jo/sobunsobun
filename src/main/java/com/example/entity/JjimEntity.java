package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "JJIM")
public class JjimEntity {

    @Id
    private BigDecimal no;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "MEMID", referencedColumnName = "id")
    private CustomerEntity customerEntity;

    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "ITEMNO", referencedColumnName = "no")
    private Item itemEntity;

    @Transient // 임시변수 == 컬럼이 생성되지 않는다. Mybatis dto 개념
    private long state; // 찜 상태 ( 찜 안되어있다? => 0, 찜 되어있다 => 1)
}
