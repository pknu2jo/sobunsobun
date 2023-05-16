package com.example.entity;

import java.util.Date;

import lombok.Data;
@Data
public class Mcategory {

	  // 중분류코드번호 - PK
	  private long code;

	  // 중분류종류(한글)
	  private String name;

	  // 대분류코드번호 - FK
	  private long lcategoryCode;
      
	  // 등록일자
	  private Date regdate;

}
