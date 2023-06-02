package com.example.service.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.ItemImage;

@Service
public interface MjItemImageService {
    
    /** 물품번호에 해당하는 이미지 중 "특정단어"가 포함되지 않은 이미지 가져오기 */
    public ItemImage findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal itemno, String filename);

    /** 물품번호에 해당하는 이미지 중 "특정단어"가 포함된 이미지 가져오기*/
    public List<ItemImage> findByItemNo_noAndFilenameLikeOrderByNoAsc(BigDecimal itemno, String filename);

    /** 이미지 저장*/
    public ItemImage saveImage(ItemImage image);

    /** 이미지 일괄 삭제 */
    public int deleteImageBatch(long[] no);

    /** 이미지번호에 해당하는 이미지정보 가져오기 */
    public ItemImage findById(BigDecimal no);



}
