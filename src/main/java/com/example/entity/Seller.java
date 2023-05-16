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
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SELLER")
public class Seller {
    

    @Id
    @Column(name="NO",length = 20)
    private String no;

    private String pw;

    private String name;

    private String phone;

    private String email;

    private String address;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // 추가시에만 날짜 정보 저장
    private Date regdate;

    @Column(name = "BLOCKCHK")
    private BigDecimal blockChk;

}
