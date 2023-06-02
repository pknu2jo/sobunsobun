package com.example.service.km;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.StorageEntity;

@Service
public interface KmAdminService {
    
    // product.do GET ------------------------------------------
    
    // 모든 보관소 정보 가져오기
    public List<StorageEntity> findAllStorage();

}
