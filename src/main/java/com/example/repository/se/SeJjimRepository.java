package com.example.repository.se;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JjimEntity;
import com.example.entity.se.SeJjimProjection;

@Repository
public interface SeJjimRepository extends JpaRepository<JjimEntity, BigDecimal> {
    
    List<SeJjimProjection> findByItemEntity_no(BigDecimal no);

    long countByCustomerEntity_idAndItemEntity_no(String id, BigDecimal no);

    @Transactional
    int deleteByCustomerEntity_idAndItemEntity_no(String id, BigDecimal no);
    
}