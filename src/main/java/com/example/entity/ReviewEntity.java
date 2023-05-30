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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "REVIEW")
@SequenceGenerator(name = "SEQ_REVIEW_NO", sequenceName = "SEQ_REVIEW_NO", initialValue = 1, allocationSize = 1)
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_NO")
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name = "RATING")
    private BigDecimal rating;
    
    @Lob
    @Column(name = "COMMENT")
    private String comment;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;
    
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEMNO", referencedColumnName = "no")
    private Item itemEntity;

    @ToString.Exclude
    @OneToMany(mappedBy = "reviewno", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<ReviewImageEntity> imageList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "ORDERNO", referencedColumnName = "NO")
    @ToString.Exclude
    private PurchaseOrderEntity purchaseOrderEntity;
    
}
