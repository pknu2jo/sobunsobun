package com.example.repository.km;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ReviewImageEntity;

@Repository
public interface KmReviewImageRepository extends JpaRepository<ReviewImageEntity, BigDecimal>{
    
}
