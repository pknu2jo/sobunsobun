package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.TotalgenderViewRepository;
import com.example.repository.TotallocationViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    final TotalgenderViewRepository tgvRepository;
    final TotallocationViewRepository tlvRepository;

    @GetMapping(value = "/home.do")
    public String homeGET(){
        return "/seller/home";
    }

    @GetMapping(value = "/item/search.do")
    public String searchGET(Model model){
        /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */        
        // 전체 여성 인원수 구하기
        long Female = tgvRepository.countByGender("F");
        // 전체 남성 인원수 구하기
        long Male = tgvRepository.countByGender("M");

        // 판매자 별 여성 남성 구하기
        // long Female = tgvRepository.countByGenderAndNo("F", "사업자등록번호");
        // long Male = tgvRepository.countByGenderAndNo("M", "사업자등록번호");                

        // html로 값 넘기기
        model.addAttribute("female", Female);
        model.addAttribute("male", Male);
        /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */        
        String[] locations = {"중구","서구","동구","영도구","진구","동래구","남구","북구","해운대구","사하구","금정구","강서구","연제구","수영구","사상구"};
        List<Long> list = new ArrayList<>();

        // 사업자번호가 없는 버전
        for(int i = 0 ; i<locations.length;i++){
            list.add(tlvRepository.countByLocationContaining(locations[i]));
        }
        log.info("리스트 찍기 => {}", list.toString());

        // 실제로 적용해야 할 것
        // for(int i = 0 ; i<locations.length;i++){
        //     list.add(tlvRepository.countByNoAndLocationContaining("4564546544", locations[i]));
        // }

        model.addAttribute("list", list);
        return "/seller/item/search";
    }
}
