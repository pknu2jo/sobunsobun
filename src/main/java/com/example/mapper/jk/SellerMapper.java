package com.example.mapper.jk;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Seller;

@Mapper
public interface SellerMapper {
    
    // 회원가입
    @Insert({ " INSERT INTO seller(no, pw, name, phone, email, address) ", 
    " VALUES(#{obj.no}, #{obj.pw}, #{obj.name}, #{obj.phone}, #{obj.email}, #{obj.address}) " })
    public int joinSeller(@Param("obj") Seller seller);

    // 로그인
    @Select({" SELECT * FROM seller WHERE no =#{no} AND pw=#{pw}"})
    public Seller sellerLogin(Seller seller);
    
    // 비밀번호 찾기 (임시비밀번호 발급)
     @Update({" Update seller SET pw = #{newPw} WHERE no =#{no} AND email=#{email}"})
    public int findSellerPw(Seller seller);

    // 정보수정 (업체명, 주소, 연락처, 이메일)
    @Update({ " UPDATE seller SET name = #{name}, phone = #{phone}, email = #{email}, address = {address} ", 
    " WHERE no = #{no} " })
    public int updateSellerinfo(Seller seller);

    // 비번변경
    @Update({ " UPDATE seller SET pw = #{newPw} WHERE no = #{no} AND pw = #{pw} " })
    public int updateSellerPw(Seller seller);
    
    // 탈퇴
    @Update({ " UPDATE seller SET no = null, pw = null, name = null, phone = null, email = null, address = null, blockchk = null, regdate = null ", 
    " WHERE no = #{no} AND pw = #{pw} " })
    public int deleteSeller(Seller seller);
}
