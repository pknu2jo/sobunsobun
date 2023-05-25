package com.example.repository.ikh;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ikh.StalocationView;

@Repository
public interface StalocationViewRepository extends JpaRepository<StalocationView, BigDecimal>{
    // 사업자등록번호, 아이템 코드 에 맞는 지역별 인원수 구하기
    // SELECT count(*) FROM STALOCATION WHERE itemcode=11 AND NO='1078198143' AND LOCATION  LIKE '%구%'
    long countByNoAndItemcodeAndLocationContaining(String no, BigDecimal itemcode ,String location);
}
