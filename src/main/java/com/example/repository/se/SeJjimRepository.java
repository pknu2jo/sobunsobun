package com.example.repository.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JjimEntity;

@Repository
public interface SeJjimRepository extends JpaRepository<JjimEntity, BigDecimal> {

    List<JjimEntity> findByItemEntity_no(BigDecimal no);
    
}