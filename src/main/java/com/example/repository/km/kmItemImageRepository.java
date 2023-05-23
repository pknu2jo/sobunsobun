package com.example.repository.km;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ItemImage;

@Repository
public interface kmItemImageRepository extends JpaRepository<ItemImage, BigDecimal> {
    
    // itemNo에 해당하는 image List 가져오기
    List<ItemImage> findByItemNo_noOrderByNoAsc(BigDecimal no);

}
