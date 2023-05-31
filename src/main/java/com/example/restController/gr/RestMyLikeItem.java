package com.example.restController.gr;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Integer> mylikeitemPOST() {

        Map<String, Integer> retMap = new HashMap<>();

        return retMap;
    }

}
