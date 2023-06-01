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

    // @Query(value = " DELETE FROM jjim WHERE memid = :memid and itemno = :itemno
    // ", nativeQuery = true)
    // public int deleteOneJjim(
    // @Param("itemno") BigDecimal itemno,
    // @Param("memid") String memid);
}
