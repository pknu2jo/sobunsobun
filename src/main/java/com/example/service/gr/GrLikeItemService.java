package com.example.service.gr;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.LikeItem;

@Service
public interface GrLikeItemService {

    // 관심물품 등록
    public int insertLikeItem(@Param("list") List<LikeItem> list);

}
