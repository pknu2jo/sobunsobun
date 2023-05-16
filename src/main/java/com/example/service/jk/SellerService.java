package com.example.service.jk;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Seller;

@Service
public interface SellerService {
    
    // 업체 회원가입
    public int joinSeller(@Param("obj") Seller obj);

    // 업체 로그인
    public Seller sellerLogin(@Param("obj") Seller obj);

    // 업체 정보수정 (이름, )
    public int updateSellerinfo(@Param("obj") Seller obj);

    // 업체 비밀번호 변경
    public int updateSellerPw(@Param("obj") Seller obj);

    // 업체 탈퇴
    public int deleteSeller(@Param("obj") Seller obj);
}
