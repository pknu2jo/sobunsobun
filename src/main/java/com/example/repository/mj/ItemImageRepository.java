package com.example.repository.mj;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, BigDecimal>{
    
    ItemImage findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal itemno, String filename);
}
