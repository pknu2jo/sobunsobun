package com.example.restController.gr;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Customer;
import com.example.dto.CustomerAddress;
import com.example.service.gr.GrCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class RestMyAccountDrop {

    final GrCustomerService cService;

    @PostMapping(value = "/myaccountdrop.json")
    public Map<String, Integer> myaccountdropPOST(@RequestBody Customer customer) {

        log.info("rkfka rest=>{}", customer.getId());
        // log.info("rkfka rest=>{}", id);
        Map<String, Integer> retMap = new HashMap<>();

        try {
            // Customer customer = new Customer();
            // customer.setId(id);
            int ret = cService.myaccountdrop(customer);
            CustomerAddress c = cService.selectOneCustomerAddress(customer.getId());
            cService.deletemyaddress(c);
            retMap.put("result", ret);

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        log.info("rkrkfa rest retmap=>{}", retMap.get("result"));
        return retMap;
    }

}
