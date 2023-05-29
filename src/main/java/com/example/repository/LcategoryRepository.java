package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Lcategory;

public interface LcategoryRepository extends JpaRepository<Lcategory, BigDecimal>{
    List<Lcategory> findAll();
    
    BigDecimal findByName(String name);

    String findByCode(BigDecimal code);
}
