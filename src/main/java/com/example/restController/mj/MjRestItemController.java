package com.example.restController.mj;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Item;
import com.example.repository.jk.JkSellerRepository;
import com.example.repository.mj.ItemImageRepository;
import com.example.repository.mj.ItemRepository;
import com.example.service.mj.MjItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/seller")
@RequiredArgsConstructor
public class MjRestItemController {
    
    final ItemRepository iRepository;
    final ItemImageRepository imageRepository;
    final JkSellerRepository sellerRepository;

    final MjItemService itemService;
    
    // 물품 등록
    @PostMapping(value = "/insertitem.json")
    public Map<String, Object> insertItemPOST(@RequestBody Item item, Model model){
        Map<String, Object> retMap = new HashMap<>();
        try {

            log.info("item=>{}", item.toString());
            Item ret = itemService.saveItem(item);
            
            retMap.put("itemno", ret.getNo());
            retMap.put("result1", ret);
            retMap.put("result", 1);
            retMap.put("message", "물품이 등록되었습니다.");
            model.addAttribute("ret", ret);
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("message", "물품이 등록되지 않았습니다.");
        }
        return retMap;
    }

    // // 물품 등록
    // @PostMapping(value = "/insertitem.json")
    // public Map<String, Object> insertItemPOST(@RequestBody Item item, Model model){
    //     Map<String, Object> retMap = new HashMap<>();
    //     log.info("item=>{}", item.toString());
    //     Item ret = iRepository.save(item);
        
    //     retMap.put("itemno", ret.getNo());
    //     retMap.put("result", ret);
    //     model.addAttribute("ret", ret);
    //     return retMap;
    // }
    
    // 물품등록시 소분류코드가 선택된 후 물품명 입력가능
    @GetMapping(value = "scodechk.json")
    public Map<String, Object> scodechkGET(@RequestParam(name = "scode", required = false)String scode){
        Map<String, Object> retMap = new HashMap<>();
        
        try {
            log.info("scode=>{}", scode);  
            if( scode == null ){
                retMap.put("result", 0);
                retMap.put("message", "소분류 카테고리를 선택해주세요.");
            } else if(scode =="" || scode.equals("000")){
                retMap.put("result", 0);
                retMap.put("message", "소분류 카테고리를 선택해주세요.");
            } else {
                retMap.put("result", 1);
                retMap.put("message", "소분류 카테고리가 선택되었습니다.");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;  
    }

    // 물품 선택삭제
    @GetMapping(value="/deleteitems.json")
    public Map<String, Object> deleteitemsGET(@RequestParam(name = "itemno")long[] no){
        Map<String, Object> retMap = new HashMap<>();

        try {
            if(no==null || no.length == 0){
                retMap.put("result", 0);
                retMap.put("message", "삭제할 물품이 선택되지 않았습니다.");
            }
            else{
                retMap.put("result", 1);
                retMap.put("message", "삭제할 물품이 선택되었습니다.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;  
        
    }

    // 물품 선택 수정
    @GetMapping(value="/updateitems.json")
    public Map<String, Object> updateitemsGET(@RequestParam(name = "itemno")long[] no){
        Map<String, Object> retMap = new HashMap<>();

        try {
            if(no==null || no.length == 0){
                retMap.put("result", 0);
                retMap.put("message", "수정할 물품이 선택되지 않았습니다.");
            }
            else{
                retMap.put("result", 1);
                retMap.put("message", "수정할 물품이 선택되었습니다.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;  
        
    }

/*======================================↓↓ 물품이미지 rest ↓↓=========================================== */
    // 물품 대표이미지 1개 등록
    @GetMapping(value = "/insertimageone.json")
    public Map<String, Object> insertimageoneGET(@RequestParam(name = "image") String image){
        Map<String, Object> retMap = new HashMap<>();
        try {
            if(image.length() > 0){
                retMap.put("result", 1);
                retMap.put("message", "파일이 선택되었습니다.");
            }
            else if(image.length() <= 0){
                retMap.put("result", 0);
                retMap.put("message", "파일을 선택해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;  
        
    }

    // 물품 상세이미지 n개 등록
    @GetMapping(value = "/insertimages.json")
    public Map<String, Object> insertimagesGET(@RequestParam(name="images") String images){
        Map<String, Object> retMap = new HashMap<>();

        try {
            if(images.length() > 0){
                retMap.put("result", 1);
                retMap.put("message", "파일이 선택되었습니다.");
            }
            else if(images.length() <= 0){
                retMap.put("result", 0);
                retMap.put("message", "파일을 1개 이상 선택해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;  
        
    }

    // 물품이미지 선택삭제
    @GetMapping(value="/deleteimages.json")
    public Map<String, Object> deleteimagesGET(@RequestParam(name = "imageno")long[] no){
        Map<String, Object> retMap = new HashMap<>();

        try {
            if(no==null || no.length == 0){
                retMap.put("result", 0);
                retMap.put("message", "삭제할 이미지가 선택되지 않았습니다.");
            }
            else{
                retMap.put("result", 1);
                retMap.put("message", "삭제할 이미지가 선택되었습니다.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;  
        
    }

    // @GetMapping(value="/insertitem.json")
    // public Map<String, Object> insertitemGET(@RequestParam(name = "scategoryCode")long scode){
    //     Map<String, Object> retMap = new HashMap<>();

    //     try {
    //         if( scode != null ){

    //         }
    //         else{

    //         }
            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         retMap.put("result", -1);
    //         retMap.put("error", e.getMessage());
    //     }
    //     return retMap;  
        
    // }
    


    

}
