package com.example.service.mj;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.ItemImage;
import com.example.mapper.mj.mjItemImageMapper;
import com.example.repository.mj.ItemImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MjItemImageServiceImpl implements MjItemImageService{

    final ItemImageRepository imageRepository;
    final mjItemImageMapper imageMapper;

    /** 물품번호에 해당하는 이미지 중 "특정단어"가 포함되지 않은 이미지 가져오기 */
    @Override
    public ItemImage findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal itemno, String filename) {
        try {
            return imageRepository.findByItemNo_noAndFilenameNotLikeOrderByNoAsc(itemno, filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 물품번호에 해당하는 이미지 중 "특정단어"가 포함된 이미지 가져오기*/
    @Override
    public List<ItemImage> findByItemNo_noAndFilenameLikeOrderByNoAsc(BigDecimal itemno, String filename) {
        try {
            return imageRepository.findByItemNo_noAndFilenameLikeOrderByNoAsc(itemno, filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 이미지 저장*/
    @Override
    public ItemImage saveImage(ItemImage image) {
        try {
            return imageRepository.save(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 이미지 일괄 삭제 */
    @Override
    public int deleteImageBatch(long[] no) {
        try {
            return imageMapper.deleteImageBatch(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 이미지번호에 해당하는 이미지정보 가져오기 */
    @Override
    public ItemImage findById(BigDecimal no) {
        try {
            return imageRepository.findById(no).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    


}
