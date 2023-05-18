package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @Column(name = "ID")
    private String id;

    @ToString.Exclude
    @Column(name = "PW")
    private String pw;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BLOCKCHK")
    private BigDecimal blockchk;
    
    @Column(name = "QUITCHK")
    private BigDecimal quitchk;

    @CreationTimestamp
    @Column(name = "REGDATE", insertable = true, updatable = false)
    private Date regdate; 
    
}
