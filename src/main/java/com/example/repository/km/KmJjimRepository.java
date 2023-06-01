package com.example.repository.km;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JjimEntity;

@Repository
public interface KmJjimRepository extends JpaRepository<JjimEntity, BigDecimal>{
    
    // 고객이 해당 아이템의 찜이 되어있는지 확인하기 (1이면 찜 되어있음, 0이면 안되어있음)
    int countByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

    @Transactional
    void deleteByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);
}
