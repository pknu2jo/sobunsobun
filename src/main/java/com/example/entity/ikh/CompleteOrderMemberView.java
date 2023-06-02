package com.example.entity.ikh;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Immutable // 뷰
@Entity
@Table(name = "COMPLETEORDERMEMBERVIEW")
public class CompleteOrderMemberView {
    
    @Column(name = "NO")
    private String no;
    
    @Column(name = "PNO")
    private BigDecimal pno;

    @Column(name = "ITEMCODE")
    private BigDecimal itemcode;
    
    @Column(name = "MEMID")
    private String memid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME")
    private String nickname;

    @Id
    @Column(name = "ENID")
    private BigDecimal enid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "REGDATE")
    private Date regdate;
}
