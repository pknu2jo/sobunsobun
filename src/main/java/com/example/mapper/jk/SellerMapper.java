package com.example.mapper.jk;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Seller;

@Mapper
public interface SellerMapper {
    
    // 회원가입
    public int joinSeller(Seller seller);

    // 로그인
    public Seller sellerLogin(Seller seller);
    
    // 비밀번호 찾기 (임시비밀번호 발급)
    public int findSellerPw(Seller seller);

    // 정보수정용 업체정보 조회
    public Seller findSellerInfo(String sellerId);

    // 정보수정 (업체명, 주소, 연락처, 이메일)
    public int updateSellerinfo(Seller seller);

    // 비번변경
    public int updateSellerPw(Seller seller);
    
    // 탈퇴 (탈퇴 기능은 Admin 페이지에서 구현해야 할 것 같음!)
    public int deleteSeller(Seller seller);

    // 아이디 중복확인
    public int idCheck(String id);
}
