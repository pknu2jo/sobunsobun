package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.StorageEntity;

@Repository
public interface KmStorageRepository extends JpaRepository<StorageEntity, BigDecimal>{    
    
    public List<StorageEntity> findAllByOrderByNameAsc();
}
