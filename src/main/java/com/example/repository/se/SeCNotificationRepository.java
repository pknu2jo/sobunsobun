package com.example.repository.se;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CNotificationEntity;

@Repository
public interface SeCNotificationRepository extends JpaRepository<CNotificationEntity, BigDecimal> {
    
}
