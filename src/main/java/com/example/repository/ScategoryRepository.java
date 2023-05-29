package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Scategory;

public interface ScategoryRepository extends JpaRepository<Scategory, BigDecimal>{
    List<Scategory> findByMcategoryCode_code(BigDecimal code);
}
