package com.example.service.se;

import org.springframework.stereotype.Service;

import com.example.entity.CustomerAddressEntity;
import com.example.entity.CustomerEntity;
import com.example.repository.se.SeCustomerAddressRepository;
import com.example.repository.se.SeCustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeCustomerServiceImpl implements SeCustomerService {
    
    final SeCustomerRepository cRepository;
    final SeCustomerAddressRepository caRepository;

    // ----------------------------------------------------------------------------------------------------------
    // 회원가입
    @Override
    public int joinCustomerOne(CustomerEntity obj, CustomerAddressEntity obj2) {
        try {
            CustomerEntity ret = cRepository.save(obj);
            CustomerAddressEntity ret2 = caRepository.save(obj2);
            if( ret != null && ret2 != null ){
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

    // ----------------------------------------------------------------------------------------------------------
    // 아이디 찾기
    @Override
    public CustomerEntity findId(CustomerEntity customerEntity) {
        try {
            return cRepository.findByNameAndEmail(customerEntity.getName(), customerEntity.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ----------------------------------------------------------------------------------------------------------
    // 비밀번호 찾기
    @Override
    public CustomerEntity findPw(String email, String phone) {
        try {
            return cRepository.findByEmailAndPhone(email, phone);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // ----------------------------------------------------------------------------------------------------------
    // 그냥 save()
    @Override
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        try {
            return cRepository.save(customerEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    // ----------------------------------------------------------------------------------------------------------
    // 그냥 findById()
    @Override
    public CustomerEntity findById(String id) {
        try {
            return cRepository.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    


}
