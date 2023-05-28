package com.example.controller.mj;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Item;
import com.example.repository.mj.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/seller")
@RequiredArgsConstructor
public class MjRestItemController {
    
    final ItemRepository iRepository;
    
    @PostMapping(value = "/insertitem.json")
    public Map<String, Object> insertItemPOST(@RequestBody Item item, Model model){
        log.info("item=>{}", item.toString());
        Item ret = iRepository.save(item);
        
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("itemno", ret.getNo());
        retMap.put("result", ret);
        model.addAttribute("ret", ret);
        return retMap;
    }

    

}
