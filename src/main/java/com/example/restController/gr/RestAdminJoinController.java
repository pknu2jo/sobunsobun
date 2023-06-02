package com.example.restController.gr;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AdminEntity;
import com.example.repository.gr.gradminRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
public class RestAdminJoinController {

    final gradminRepository aRepository;

    // 회원가입 (admin 하나만 생성)
    @PostMapping(value = "/join.json")
    public Map<String, Integer> joinPOST(@RequestBody AdminEntity admin) {

        log.info("회원가입 => {}", admin.toString());
        Map<String, Integer> retMap = new HashMap<>();

        try {

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            admin.setId(admin.getId());
            admin.setPw(bcpe.encode(admin.getPw()));
            admin.setName(admin.getName());

            aRepository.save(admin);

            retMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;

    }

}
