package com.example.service.gr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.LikeItem;
import com.example.mapper.gr.GrLikeItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrLikeItemServiceImpl implements GrLikeItemService {

    final GrLikeItemMapper lMapper;

    // 관심물품등록
    @Override
    public int insertLikeItem(List<LikeItem> list) {
        try {
            return lMapper.insertLikeItem(list);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
