package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "ITEMIMAGE")
@SequenceGenerator(name = "SEQ_IMAGE_NO", sequenceName = "SEQ_IMAGE_NO", initialValue = 1, allocationSize = 1)
public class ItemImage {
    
    // 물품이미지번호(시퀀스) - PK
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMAGE_NO")
    @Column(name = "NO")
    private BigDecimal no;	  

    // 물품이미지이름
    @Column(name = "FILENAME")
    private String filename;	  

    // 물품이미지데이터
    @Lob
    @Column(name = "FILEDATA")
    @ToString.Exclude
    private byte[] filedata;	  

    // 물품이미지타입
    @Column(name = "FILETYPE")
    private String filetype;	  

    // 물품이미지사이즈
    @Column(name = "FILESIZE")
    private BigDecimal filesize;	  

    // 등록날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable=false)
    private Date regdate;	  

    // 물품번호(시퀀스) - FK
    @Column(name = "ITEMNO")
    private BigDecimal itemNo;
}