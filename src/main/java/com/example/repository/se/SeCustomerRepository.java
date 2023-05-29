package com.example.repository.se;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CustomerEntity;

@Repository
public interface SeCustomerRepository extends JpaRepository<CustomerEntity, String> {

    // 아이디 중복확인
    int countById(String id);

    // 아이디 찾기
    CustomerEntity findByNameAndEmail(String name, String email);
    
    // 비밀번호 찾기
    CustomerEntity findByEmailAndPhone(String email, String phone);
    
}
