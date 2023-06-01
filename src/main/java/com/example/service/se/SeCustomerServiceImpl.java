package com.example.service.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.CNotificationEntity;
import com.example.entity.CustomerAddressEntity;
import com.example.entity.CustomerEntity;
import com.example.entity.se.SeJjimProjection;
import com.example.repository.se.SeCNotificationRepository;
import com.example.repository.se.SeCustomerAddressRepository;
import com.example.repository.se.SeCustomerRepository;
import com.example.repository.se.SeJjimRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeCustomerServiceImpl implements SeCustomerService {
    
    final SeCustomerRepository cRepository;
    final SeCustomerAddressRepository caRepository;
    final SeJjimRepository jjimRepository;
    final SeCNotificationRepository notiRepository;

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

    // ----------------------------------------------------------------------------------------------------------
    // 해당 아이템을 찜하고 있는 회원아이디 전부 불러오기
    @Override
    public List<SeJjimProjection> findByItemEntity_no(BigDecimal itemno) {
        try {
            return jjimRepository.findByItemEntity_no(itemno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ----------------------------------------------------------------------------------------------------------
    // 알림 추가
    @Override
    public int saveCNotification(CNotificationEntity obj) {
        try {
            CNotificationEntity ret = notiRepository.save(obj);
            if(ret != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    


}
