package com.example.restController.gr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.CustomerEntity;
import com.example.entity.Item;
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

            JjimEntity jjimEntity = new JjimEntity();

            int ret = grRepository.countByCustomerEntity_idAndItemEntity_no(map.get("id").toString(),
                    BigDecimal.valueOf(Long.parseLong(map.get("itemno").toString())));
            log.info("가람 => {}", ret);

            if (ret == 1) {
                grRepository.deleteByCustomerEntity_idAndItemEntity_no(map.get("id").toString(),
                        BigDecimal.valueOf(Long.parseLong(map.get("itemno").toString())));
                retMap.put("result", 1);

            } else {
                log.info("skdhkskdhk=>{}", jjimEntity.toString());
                CustomerEntity cEntity = new CustomerEntity();
                cEntity.setId(map.get("id").toString());
                jjimEntity.setCustomerEntity(cEntity);

                // jjimEntity.getCustomerEntity().setId((map.get("id").toString()));
                // log.info("skdhkskdhk=>{}", jjimEntity.getCustomerEntity().getId());

                Item iEntity = new Item();
                iEntity.setNo(BigDecimal.valueOf(Long.parseLong(map.get("itemno").toString())));

                // jjimEntity.getItemEntity().setNo(BigDecimal.valueOf(Long.parseLong(map.get("itemno").toString())));
                jjimEntity.setItemEntity(iEntity);

                log.info("dkdkdk => {}", jjimEntity.toString());
                grRepository.save(jjimEntity);
                retMap.put("result", 1);
            }

        } catch (Exception e) {
            retMap.put("result", 0);
        }

        return retMap;
    }

}
//
