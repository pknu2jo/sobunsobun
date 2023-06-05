package com.example.repository.gr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.CustomerEntity;

@Repository
public interface grcustomerRepository extends JpaRepository<CustomerEntity, String> {

    List<CustomerEntity> findAll();

    // CustomerEntity findById();

    @Query(value = "SELECT COUNT(*) FROM Customer WHERE DATE_TRUNC('day', regdate) = CURRENT_DATE", nativeQuery = true)
    int countTodayCustomers();

    @Query(value = "SELECT COUNT(*) FROM Customer WHERE quitchk = 1", nativeQuery = true)
    int countByQuitchk();

    @Query(value = "SELECT COUNT(*) FROM PURCHASESTATUS WHERE DATE_TRUNC('day', regdate) = CURRENT_DATE AND STATE=1 AND CANCEL=0", nativeQuery = true)
    int coutByPurchase();

}
