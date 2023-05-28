package com.example.mapper.gr;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Customer;
import com.example.dto.CustomerAddress;

@Mapper
public interface GrCustomerMapper {

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

    // 회원탈퇴 -> 주소 삭제
    public int deletemyaddress(CustomerAddress customeraddress);

    // 카카오톡 로그인 아이디 찾기
    public Customer searchkakao(String id);

}
