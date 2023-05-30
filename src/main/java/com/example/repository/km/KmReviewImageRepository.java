package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ReviewImageEntity;
import com.example.entity.km.KmReviewNoProjection;

@Repository
public interface KmReviewImageRepository extends JpaRepository<ReviewImageEntity, BigDecimal>{
    
    // reviewNo에 해당하는 image List 가져오기
    List<KmReviewNoProjection> findByReviewno_noOrderByNoAsc(BigDecimal reviewNo);
}
