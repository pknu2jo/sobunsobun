package com.example.controller.mj;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Item;
import com.example.entity.ItemImage;
import com.example.repository.mj.ItemImageRepository;
import com.example.repository.mj.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/seller/itemimage")
@RequiredArgsConstructor
public class MjItemImageController {
    
    final ItemRepository iRepository;
    final ItemImageRepository imageRepository;

    final ResourceLoader resourceLoader; //resources폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String DEFAULTIMAGE;

    //127.0.0.1:5959/SOBUN/seller/itemimage/image?no=1
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name="no", defaultValue = "0") long no) throws IOException{
        ItemImage obj = imageRepository.findById(BigDecimal.valueOf(no)).orElse(null);

        HttpHeaders headers = new HttpHeaders();
        if(obj != null) { // 이미지가 존재할 경우
            if(obj.getFilesize().longValue() > 0){// 이미지가 존재하는지 확인
                headers.setContentType( MediaType.parseMediaType(obj.getFiletype()));
                return new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);
            }
        }

        //이미지가 없을 경우
        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }





    @GetMapping(value = "/updateimage.do")
    public String updateimageGET(Model model, HttpServletRequest request, @RequestParam(name = "no")long no){
        try {
            Item item = iRepository.findById(BigDecimal.valueOf(no)).orElse(null);
            
            // 대표이미지
            // String a = "%상세%";
            ItemImage image1 = new ItemImage();
            image1.setNo(BigDecimal.valueOf(0));
            ItemImage image = imageRepository.findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal.valueOf(no), "%상세%");
            item.setImageUrl( request.getContextPath() + "/seller/itemimage/image?no=" + 0);
            if(image != null){
                item.setImageUrl( request.getContextPath() + "/seller/itemimage/image?no=" + image.getNo().longValue() );
                log.info("image {}", image.toString());
            }
            model.addAttribute("item", item);
            
            
            // ItemImage image = imageRepository.
            log.info("itemno => {}", no);

            return "/mj/seller/itemimage/updateimage";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }

    }

    
    
}
