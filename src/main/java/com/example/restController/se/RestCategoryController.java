package com.example.restController.se;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CategoryAll;
import com.example.mapper.se.CategoryAllMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/testcate")
@Slf4j
@AllArgsConstructor
public class RestCategoryController {
    
    final CategoryAllMapper cAllMapper;

    // 127.0.0.1:5959/SOBUN/api/testcate/1.json
    @GetMapping(value = "/1.json")
    public Map<String, Object> lcategoryGET() {
        // log.info("SobunA => {}", "왔나요");
        Map<String, Object> retMap = new HashMap<>();

        // List<CategoryAll> list = cAllMapper.selectCategoryAll();
        // log.info("카테고리 목록 확인 => {}", list.toString());
        // retMap.put("list", list);
        return retMap;
    } 

}
