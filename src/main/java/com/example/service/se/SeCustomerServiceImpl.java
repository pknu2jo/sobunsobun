package com.example.service.se;

import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.repository.se.SeCustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeCustomerServiceImpl implements SeCustomerService {
    
    final SeCustomerRepository cRepository;

    // ----------------------------------------------------------------------------------------------------------
    // 회원가입
    @Override
    public int joinCustomerOne(Customer obj) {
        try {
            Customer ret = cRepository.save(obj);
            if( ret != null ){
                return 1;
            }
            else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // ----------------------------------------------------------------------------------------------------------
    // 아이디 중복확인
    @Override
    public int idCheck(String id) {
        try {
            return cRepository.countById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
