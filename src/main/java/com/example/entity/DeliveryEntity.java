package com.example.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "DELIVERY")
public class DeliveryEntity {

    @Id    
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name ="STATUS")
    private String status;
}
