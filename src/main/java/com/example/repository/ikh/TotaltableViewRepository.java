package com.example.repository.ikh;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ikh.TotaltableView;

@Repository
public interface TotaltableViewRepository extends JpaRepository<TotaltableView, Date>{
    
    // 사업자 번호에 맞는 테이블 구하기
    List<TotaltableView> findByNo(String no);
}
