package com.example.repository.jk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.SellerEntity;

@Repository
public interface JkSellerRepository extends JpaRepository<SellerEntity, String> {

    // 아이디 중복확인
    int countByNo(String id);

    // 아이디(사업자번호)별 정보조회
    SellerEntity findByNo (String no);
}
