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

import lombok.Data;
import lombok.ToString;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCATEGORYCODE", referencedColumnName = "CODE")
    private Scategory scategoryCode;

    @ToString.Exclude
    @OneToMany(mappedBy = "itemNo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<ItemImage> imageList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "itemEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<ReviewEntity> reviewList = new ArrayList<>();
}
