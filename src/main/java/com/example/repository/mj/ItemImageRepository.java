package com.example.repository.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, BigDecimal>{
    
    /** 물품번호에 해당하는 이미지 중 "특정단어"가 포함되지 않은 이미지 가져오기 */
    ItemImage findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal itemno, String filename);
    
    /** 물품번호에 해당하는 이미지 중 "특정단어"가 포함된 이미지 가져오기*/
    List<ItemImage> findByItemNo_noAndFilenameLikeOrderByNoAsc(BigDecimal itemno, String filename);

    // @Modifying
    // @Query("DELETE FROM ITEMIMAGE i WHERE i.no IN :imageno")
    // int deleteAllByNo(@Param("imageno") BigDecimal[] imageno);
}
