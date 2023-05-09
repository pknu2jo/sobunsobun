package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Admin;
import com.example.dto.Customer;
import com.example.dto.Seller;

@Mapper
public interface SecurityMapper {
    
    // 회원 정보 모두 불러오기
    @Select({ " SELECT m.* FROM customer m WHERE m.id=#{id} " })
    public Customer selectCustomerOne(String id);

    // 업체 정보 모두 불러오기
    @Select({ " SELECT s.* FROM seller s WHERE s.no=#{no} " })
    public Seller selectSellerOne(String no);

    // 관리자 정보 모두 불러오기
    @Select({ " SELECT a.* FROM admin a WHERE a.id=#{id} " })
    public Admin selectAdminOne(String id);
}
