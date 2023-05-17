package com.example.mapper.km;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Customer;

@Mapper
public interface KmCustomerMapper {
    
    // 회원가입
    @Insert({ " INSERT INTO customer(id, pw, name, phone, email, nickname, gender) ", 
    " VALUES(#{id}, #{pw}, #{name}, #{phone}, #{email}, #{nickname}, #{gender}) " })
    public int joinCustomer(Customer customer);

    // 상품에 대한 열린 공구 가져오기 -> 남은 인원
    public int countRemainingPerson(long purchaseno);

    // 상품에 대한 열린 공구 가져오기 -> 참여인원, 보관소, 마감기한 마감순 정렬 (추후 보관소 table 생성 되면 보관소도 불러오기)
    
    
}
