package com.example.mapper.km;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Customer;

@Mapper
public interface KmCustomerMapper {
    
    // 회원가입
    @Insert({ " INSERT INTO customer(id, pw, name, phone, email, nickname, gender) ", 
    " VALUES(#{id}, #{pw}, #{name}, #{phone}, #{email}, #{nickname}, #{gender}) " })
    public int joinCustomer(Customer customer);

    // 상품에 대한 열린 공구 가져오기 -> 남은 인원
    public int countRemainingPerson(long purchaseno);
    
}
