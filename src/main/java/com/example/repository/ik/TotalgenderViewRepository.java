package com.example.repository.ik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ik.TotalgenderView;

@Repository
public interface TotalgenderViewRepository extends JpaRepository<TotalgenderView, String>{
    // 사업자등록번호에 맞는 성별 인원수 구하기
    long countByGenderAndNo(String gender, String no);
}
