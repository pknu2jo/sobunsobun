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
@Table(name = "Storage")
@SequenceGenerator(name = "seq_storage_no", sequenceName = "seq_storage_no", initialValue = 1, allocationSize = 1)
public class StorageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_storage_no")
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name = "NAME")
    private String name;
    
    private String phone;
    private String postcode;
    private String address1;
    private String address2;
    private String address3;

    private BigDecimal latitude;
    private BigDecimal longitude;

    // @manyto 관리자 아이디
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADMINID", referencedColumnName = "ID")
    private AdminEntity adminEntity;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    @ToString.Exclude
    @OneToMany(mappedBy = "storageEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<PurchaseEntity> purchaseList = new ArrayList<>();

}
