package com.example.entity;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
@Entity
@Table(name="SCATEGORY")
public class Scategory {

	// 소분류코드번호 - PK
	@Column(name="CODE")
	@Id
	private BigDecimal code;

	// 소분류종류(한글)
	@Column(name="NAME")
	private String name;

	// 중분류코드번호 - FK
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MCATEGORYCODE", referencedColumnName = "CODE")
	private Mcategory mcategoryCode;
	
	// 등록일자
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
	@Column(name="REGDATE", updatable = false)
	private Date regdate;
}
