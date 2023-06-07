package com.example.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "PURCHASE")
@SequenceGenerator(name = "SEQ_PURCHASE_NO", sequenceName = "SEQ_PURCHASE_NO", initialValue = 1, allocationSize = 1)
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PURCHASE_NO")
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name = "participant")
    private BigDecimal participant;
    

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "deadline", updatable = false)
    private Date deadline;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    @Column(name = "receivestate")
    private BigDecimal receiveState;
    @Column(name = "headcount")
    private BigDecimal headCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "receivedate", updatable = false)
    private Date receivedate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	@JoinColumn(name = "DELIVERYNO", referencedColumnName = "NO")
    private DeliveryEntity deliveryEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	@JoinColumn(name = "STORAGENO", referencedColumnName = "NO")
    private StorageEntity storageEntity;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "purchaseEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<PurchaseOrderEntity> purchaseOrderList = new ArrayList<>();
    
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "purchaseEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<PurchaseStatusEntity> purchaseStatusList = new ArrayList<>();

}
