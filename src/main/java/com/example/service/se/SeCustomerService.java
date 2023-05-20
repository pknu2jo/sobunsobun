package com.example.service.se;

import org.springframework.stereotype.Service;

import com.example.entity.CustomerAddressEntity;
import com.example.entity.CustomerEntity;

@Service
public interface SeCustomerService {
    
    // ----------------------------------------------------------------------------------------------------------
    // 회원가입
    public int joinCustomerOne(CustomerEntity obj, CustomerAddressEntity obj2);

    // ----------------------------------------------------------------------------------------------------------
    // 아이디 중복확인
    public int idCheck(String id);

    // ----------------------------------------------------------------------------------------------------------
    // 아이디 찾기
    public CustomerEntity findId(CustomerEntity customerEntity);
}
