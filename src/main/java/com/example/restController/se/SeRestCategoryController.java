package com.example.restController.se;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CategoryAll;
import com.example.service.se.SeCategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/category")
@AllArgsConstructor
public class SeRestCategoryController {
    
    final SeCategoryService cService;

    @GetMapping(value = "/select.json")
    public Map<String, Object> selectGET(){
        Map<String, Object> retMap = new HashMap<>();
        List<CategoryAll> llist = cService.selectLCategory();
        // log.info("대분류 카테고리 => {}", llist.toString());
        List<CategoryAll> mlist = cService.selectMCategory();
        // log.info("중분류 카테고리 => {}", mlist.toString());
        List<CategoryAll> slist = cService.selectSCategory();
        // log.info("소분류카테고리 => {}", slist.toString());
        retMap.put("llist", llist);
        retMap.put("mlist", mlist);
        retMap.put("slist", slist);
        // log.info("RestCategoryController", retMap.toString());
        return retMap;
    }

}
