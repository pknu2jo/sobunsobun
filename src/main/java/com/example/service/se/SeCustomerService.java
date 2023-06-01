package com.example.service.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.CNotificationEntity;
import com.example.entity.CustomerAddressEntity;
import com.example.entity.CustomerEntity;
import com.example.entity.se.SeJjimProjection;

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
    
    // ----------------------------------------------------------------------------------------------------------
    // 비밀번호 찾기
    public CustomerEntity findPw(String email, String phone);

    // ----------------------------------------------------------------------------------------------------------
    // save()
    public CustomerEntity saveCustomer(CustomerEntity customerEntity);

    // ----------------------------------------------------------------------------------------------------------
    // findById()
    public CustomerEntity findById(String id);

    // ----------------------------------------------------------------------------------------------------------
    // 해당 아이템을 찜하고 있는 회원아이디 전부 불러오기
    public List<SeJjimProjection> findByItemEntity_no(BigDecimal itemno);

    // 알림 추가
    public int saveCNotification(CNotificationEntity obj);
}
