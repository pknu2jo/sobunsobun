package com.example.service.jk;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Seller;
import com.example.entity.SellerEntity;

@Service
public interface JkSellerService {

    /*--------------------------- Mapper 기능 ---------------------------*/

    // 업체 회원가입
    public int joinSeller(@Param("obj") Seller obj);

    // 업체 로그인
    public Seller sellerLogin(@Param("obj") Seller obj);

    // 업체 비밀번호 찾기
    public int findSellerPw(@Param("obj") Seller obj);

    // 업체 정보수정
    public int updateSellerinfo(@Param("obj") Seller obj);

    // 정보수정용 업체 조회
    public Seller findSellerInfo(String sellerId);

    // 업체 비밀번호 변경
    public int updateSellerPw(@Param("obj") Seller obj);

    // 업체 탈퇴
    public int deleteSeller(@Param("obj") Seller obj);
    
    /* --------------------------- Repository 기능 --------------------------- */

    // 아이디 중복확인
    public int countByNo(String id);

    // 아이디와 매칭되는 email 확인
    public SellerEntity findByNo(String id);

    // 저장
    public SellerEntity saveObject(SellerEntity entity);
}
