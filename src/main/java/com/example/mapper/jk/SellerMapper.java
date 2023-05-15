package com.example.mapper.jk;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Seller;

@Mapper
public interface SellerMapper {
    
    // 회원가입
    @Insert({ " INSERT INTO seller(no, pw, name, phone, email, address) ", 
    " VALUES(#{no}, #{pw}, #{name}, #{phone}, #{email}, #{address}) " })
    public int joinSeller(Seller seller);

    // 정보수정 (업체명, 주소, 연락처, 이메일)
    @Update({ " UPDATE seller SET name = #{name}, phone = #{phone}, email = #{email}, address = {address} ", 
    " WHERE no = #{no} " })
    public int updateSellerinfo(Seller seller);

    // 비번변경
    @Update({ " UPDATE seller SET pw = #{newPw} ", 
    " WHERE no = #{no} AND pw = #{pw} " })
    public int updateSellerPw(Seller seller);
    // 탈퇴
    @Update({ " UPDATE seller SET no = null, pw = null, name = null, phone = null, email = null, address = null, blockchk = null, regdate = null ", 
    " WHERE no = #{no} AND pw = #{pw} " })
    public int deleteSeller(Seller seller);
}
