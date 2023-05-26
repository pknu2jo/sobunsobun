package com.example.repository.se;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CustomerAddressEntity;
import java.util.List;


@Repository
public interface SeCustomerAddressRepository extends JpaRepository<CustomerAddressEntity, BigDecimal> {

    List<CustomerAddressEntity> findByCustomer_id(String id);
    
}
