package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.TotaltableView;

@Repository
public interface TotaltableViewRepository extends JpaRepository<TotaltableView, BigDecimal>{
    
    // 사업자 번호에 맞는 테이블 구하기
    List<TotaltableView> findByNo(String no);
}
