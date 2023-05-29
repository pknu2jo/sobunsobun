package com.example.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Delivery")
public class DeliveryEntity {

    @Id
    private BigDecimal no;

    private String status;

    @ToString.Exclude
    @OneToMany(mappedBy = "deliveryEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<PurchaseEntity> purchaseList = new ArrayList<>();
}
