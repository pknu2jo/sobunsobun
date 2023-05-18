package com.example.service.se;

import org.springframework.stereotype.Service;

import com.example.entity.Customer;

@Service
public interface SeCustomerService {
    
    // ----------------------------------------------------------------------------------------------------------
    // 회원가입
    public int joinCustomerOne(Customer obj);

    // ----------------------------------------------------------------------------------------------------------
    // 아이디 중복확인
    public int idCheck(String id);
}
