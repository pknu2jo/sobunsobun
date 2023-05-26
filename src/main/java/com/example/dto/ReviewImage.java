package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "filedata" })
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {
	
	private long no; // 이미지번호(시퀀스) - PK
	private String filename; // 물품이미지이름
	private byte[] filedata; // 물품이미지데이터
	private String filetype; // 물품이미지타입
	private long filesize; // 물품이미지사이즈
	private Date regdate; // 등록날짜
	private long reviewNo; // 리뷰번호(시퀀스) - FK
}