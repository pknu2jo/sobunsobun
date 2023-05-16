package com.example.entity;

import java.util.Date;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"filedata"})
@NoArgsConstructor
@AllArgsConstructor
public class ItemImage {
    
	  // 물품이미지번호(시퀀스) - PK
	  @Id
      private long no;	  

	  // 물품이미지이름
	  private String filename;	  

	  // 물품이미지데이터
	  private byte[] filedata;	  

	  // 물품이미지타입
	  private String filetype;	  

	  // 물품이미지사이즈
	  private long filesize;	  

	  // 등록날짜
	  private Date regdate;	  

	  // 물품번호(시퀀스) - FK
	  private long itemNo;
	}