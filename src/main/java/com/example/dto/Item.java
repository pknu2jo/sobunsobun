package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Item {

	// 물품번호(시퀀스) - PK
	private long no;

	// 물품명
	private String name;

	// 가격
	private long price;

	// 수량
	private long quantity;

	// 등록일자
	private Date regdate;

	// 사업자등록번호(0으로 시작하는 사업자번호가 있을수 있으니 String으로!) - FK
	private String regno;

	// 소분류코드번호 - FK
	private long scategoryCode;
}
