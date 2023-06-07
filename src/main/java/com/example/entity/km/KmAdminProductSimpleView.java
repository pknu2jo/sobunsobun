package com.example.entity.km;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.dto.KmAdminPurchaseStatusDTO;

import lombok.Data;

@Data
@Immutable // 뷰일경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "kmadminproductsimpleview")
public class KmAdminProductSimpleView {

    @Id
    @Column(name = "purchaseno")
    private BigDecimal purchaseno;

    @Column(name = "participant")
    private BigDecimal participant;

    @Column(name = "headcount")
    private BigDecimal headcount;

    @Column(name = "receivestate")
    private BigDecimal receivestate;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSS")
    @Column(name = "receivedate")
    private Date receivedate;

    @Column(name = "storageno")
    private BigDecimal storageno;
    
    @Column(name = "storagename")
    private String storagename;

    @Column(name = "itemno")
    private BigDecimal itemno;

    @Column(name = "itemname")
    private String itemname;   
    

    @Transient // 임시변수 == 컬럼이 생성되지 않는다. Mybatis dto 개념
    private List<KmAdminPurchaseStatusDTO> customerList; // 고객의 아이디, 고객의 물품 수령상태를 나타냄
}
