package com.example.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable // 뷰
@Entity
@Table(name = "STAGENDER")
public class StagenderView {
    
    @Column(name = "NO")
    private String no;

    @Id
    @Column(name = "ITEMCODE")
    private BigDecimal itemcode;
    
    @Column(name = "ITEMNAME")
    private String itemname;

    @Column(name = "GENDER")
    private String gender;
}
