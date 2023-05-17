package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="LCATEGORY")
public class Lcategory {
	  
	// 대분류코드번호 - PK
	@Column(name="CODE")
	@Id
	private BigDecimal code;
	
	// 대분류종류(한글)
	@Column(name="NAME")
	private String name;
	
	// 등록일자
	@ToString.Exclude
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
	@Column(name="REGDATE", updatable=false)
	private Date regdate;

}
