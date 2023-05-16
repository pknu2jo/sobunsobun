package com.example.mapper.km;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Customer;

@Mapper
public interface CustomerMapper {
    
    // 회원가입
    @Insert({ " INSERT INTO customer(id, pw, name, phone, email, nickname, gender) ", 
    " VALUES(#{id}, #{pw}, #{name}, #{phone}, #{email}, #{nickname}, #{gender}) " })
    public int joinCustomer(Customer customer);

    // 회원정보 불러오기
    // @Select({" SELECT m.id, m.name, m.phone, m.email, m.nickname, m.gender ",  
    //          " FROM Customer m WHERE m.id=#{id} "})
    public Customer selectCustomerOne(String id);
    
}
