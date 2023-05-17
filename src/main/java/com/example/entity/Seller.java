package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name = "SELLER")
public class Seller {
    

    @Id
    @Column(name="NO",length = 20)
    private String no;

    @Column(name="PW")
    private String pw;

    @Column(name="NAME")
    private String name;

    @Column(name="PHONE")
    private String phone;

    @Column(name="EMAIL")
    private String email;

    @Column(name="ADDRESS")
    private String address;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // 추가시에만 날짜 정보 저장
    @Column(name="REGDATE", updatable = false)
    private Date regdate;

    @Column(name = "BLOCKCHK")
    private BigDecimal blockChk;

}
