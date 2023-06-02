package com.example.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.StorageEntity;

public interface StorageRepository extends JpaRepository<StorageEntity, BigDecimal>{    
    
}
