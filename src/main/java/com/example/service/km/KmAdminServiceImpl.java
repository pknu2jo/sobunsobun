package com.example.service.km;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.StorageEntity;
import com.example.repository.StorageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KmAdminServiceImpl implements KmAdminService {
    
    final StorageRepository storageR;

    // 모든 보관소 정보 가져오기
    @Override
    public List<StorageEntity> findAllStorage() {
        try {
            return storageR.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
