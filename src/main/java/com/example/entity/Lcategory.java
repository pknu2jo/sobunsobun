package com.example.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Lcategory {
	  // 대분류코드번호 - PK
	  private long code;
	  
	  // 대분류종류(한글)
	  private String name;
	  
	  // 등록일자
	  private Date regdate;

}
