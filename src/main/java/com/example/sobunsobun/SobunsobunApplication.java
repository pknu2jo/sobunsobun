package com.example.sobunsobun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.mapper"}) // 매퍼 위치 설정
@ComponentScan(basePackages = {"com.example.controller",
								"com.example.restController",
							    "com.example.service",
								"com.example.config"}) // 컨트롤러, 서비스, 시큐리티 위치 설정
public class SobunsobunApplication {

	public static void main(String[] args) {
		SpringApplication.run(SobunsobunApplication.class, args);
	}

}
