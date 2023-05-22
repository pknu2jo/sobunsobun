package com.example.mapper.gr;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Customer;

@Mapper
public interface GrCustomerMapper {

    // 아이디만 전달
    public Customer selectCustomerOne1(String userid);

    // 전체 공구 참여 횟수 카운트
    public int countPurchase(String id);

}
