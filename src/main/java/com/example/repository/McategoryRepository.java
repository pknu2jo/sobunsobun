package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Mcategory;

public interface McategoryRepository extends JpaRepository<Mcategory, BigDecimal>{
    List<Mcategory> findByLcategoryCode_code(BigDecimal code);
}
