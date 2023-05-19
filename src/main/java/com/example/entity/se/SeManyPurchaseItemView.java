package com.example.entity.se;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable // 뷰일경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "SEMANYPURCHASEITEMVIEW")
public class SeManyPurchaseItemView {
    
    @Id
    @Column(name = "no")
    private BigDecimal no;
    
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "purchasecnt")
    private long purchasecnt;
}
