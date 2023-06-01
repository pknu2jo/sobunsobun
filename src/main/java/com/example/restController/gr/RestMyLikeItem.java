package com.example.restController.gr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.JjimEntity;
import com.example.repository.gr.grjjimRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class RestMyLikeItem {

    final grjjimRepository grRepository;

    @PostMapping(value = "/mylikeitem.json")
    public Map<String, Integer> mylikeitemPOST(@RequestBody Map<String, Object> map) {

        Map<String, Integer> retMap = new HashMap<>();

        try {
            log.info("아무거나 => {}", map);
            // JjimEntity obj = grRepository.findByCustomerEntity_idAndItemEntity_no("gr9",
            // BigDecimal.valueOf(9));
            // log.info("아아 => {}", obj);
            grRepository.deleteByCustomerEntity_idAndItemEntity_no("gr9", BigDecimal.valueOf(9));
            retMap.put("result", 1);
        } catch (Exception e) {
            retMap.put("result", 0);
        }

        return retMap;
    }

}
