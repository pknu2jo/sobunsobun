package com.example.repository.gr;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.JjimEntity;

@Repository
public interface grjjimRepository extends JpaRepository<JjimEntity, BigDecimal> {

    // 삭제
    @Transactional
    void deleteByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

    JjimEntity findByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

    // 아이디와 아이템번호가 같을 경우 카운트
    // 있으면 1, 없으면 0
    int countByCustomerEntity_idAndItemEntity_no(String memid, BigDecimal itemno);

}
