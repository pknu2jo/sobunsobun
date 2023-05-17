package com.example.sobunsobun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@MapperScan(basePackages = { "com.example.mapper" }) // 매퍼 위치 설정
@ComponentScan(basePackages = { "com.example.controller",
		"com.example.controller.gr",
		"com.example.restController",
		"com.example.restController.gr",
		"com.example.service",
		"com.example.service.gr",
		"com.example.config" }) // 컨트롤러, 서비스, 시큐리티 위치 설정

@PropertySource(value = { "classpath:/global.properties" })
@MapperScan(basePackages = { "com.example.mapper" }) // 매퍼 위치 설정
@ComponentScan(basePackages = { "com.example.controller",
		"com.example.restController",
		"com.example.service",
		"com.example.config" }) // 컨트롤러, 서비스, 시큐리티 위치 설정
@EntityScan(basePackages = { "com.example.entity" }) // 엔티티 위치
@EnableJpaRepositories(basePackages = { "com.example.repository" }) // 저장소 위치

public class SobunsobunApplication {

	public static void main(String[] args) {
		SpringApplication.run(SobunsobunApplication.class, args);
	}

}
