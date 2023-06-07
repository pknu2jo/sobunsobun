package com.example.repository.se;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CNotificationEntity;

@Repository
public interface SeCNotificationRepository extends JpaRepository<CNotificationEntity, BigDecimal> {
    
    public List<CNotificationEntity> findByCustomerEntity_idAndRegdateAfter(String id, Date regdate);

    @Transactional
    public int deleteByRegdateBefore(Date regdate);

}
