package com.example.repository.mj;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Lcategory;

@Repository
public interface LcateRepository extends JpaRepository<Lcategory, BigDecimal>{
    
}
