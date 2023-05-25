package com.example.service.gr;

import org.springframework.stereotype.Service;

import com.example.dto.Customer;
import com.example.dto.CustomerAddress;

@Service
public interface GrCustomerService {

    // 아이디를 이용해서 고객 정보 받아오기
    public Customer selectCustomerOne1(String userid);

    // 전체 공구 참여 횟수 카운트
    public int countPurchase(String id);

    // 회원탈퇴
    public int myaccountdrop(Customer customer);

    // 회원정보수정
    public int updateinfo(Customer customer);

    // 회원주소조회
    public CustomerAddress selectOneCustomerAddress(String id);

    // 회원주소수정
    public int updateaddress(CustomerAddress customeraddress);

    // 회원 비밀번호 변경
    public int updatepw(Customer customer);

}
