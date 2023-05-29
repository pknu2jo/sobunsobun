package com.example.repository.ikh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ikh.TotallocationView;

@Repository
public interface TotallocationViewRepository extends JpaRepository<TotallocationView, String>{    

    // 사업자등록번호에 맞는 지역별 인원수 구하기
    // SELECT count(*) FROM TOTALLOCATION t WHERE LOCATION LIKE '%남구%' AND NO='4564546544'
    long countByNoAndLocationContaining(String no, String location);    
}
