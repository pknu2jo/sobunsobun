package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = { "pw", "newPw", "newPwCheck" })
@Table(name = "SELLER")
public class SellerEntity {    

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

    @Transient // 임시변수 == 컬럼이 생성되지 않는다. Mybatis dto 개념
    private String newPw;

    @Transient // 임시변수 == 컬럼이 생성되지 않는다. Mybatis dto 개념
    private String newPwCheck;

}
