package com.example.repository.se;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ItemImage;

@Repository
public interface SeItemImageRepository extends JpaRepository<ItemImage, BigDecimal> {
    
    // 물품 번호를 보내면 이미지 정보를 반환
    List<ItemImage> findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal itemno, String filename);
}
