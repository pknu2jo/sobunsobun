package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, BigDecimal>{
    
    ItemImage findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal itemno, String filename);

    List<ItemImage> findByItemNo_noAndFilenameLikeOrderByNoAsc(BigDecimal itemno, String filename);

    // @Modifying
    // @Query("DELETE FROM ITEMIMAGE i WHERE i.no IN :imageno")
    // int deleteAllByNo(@Param("imageno") BigDecimal[] imageno);
}
