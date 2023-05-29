package com.example.controller.mj;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Item;
import com.example.entity.ItemImage;
import com.example.mapper.mj.mjItemImageMapper;
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
    final mjItemImageMapper imageMapper;

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

    /* =============================이미지등록================================= */ 

    @PostMapping(value = "/insertimage.do")
    public String insertImagePOST(@ModelAttribute ItemImage obj, @RequestParam(name = "tmpFile", required = false) MultipartFile file,
                                @RequestParam(name = "tmpFile[]", required = false) MultipartFile[] files,
                                @RequestParam(name = "item.no", required = false) BigDecimal itemno,
                                @RequestParam(name = "image", required = false) long image
                                ) throws IOException{
            log.info("물품번호=>{}", itemno.toString());
            log.info("이미지=>{}", image);
            if(file != null){
                log.info("file=>{}", file.getOriginalFilename());
            }
            if(files != null){
                for(MultipartFile m : files){
                    log.info("files=>{}", m.getOriginalFilename());
                }
            }
        try {
            // 대표이미지 등록/수정
            if(image==0){
                // 물품번호에 해당하는 이미지 중 "상세"가 포함되지않은 이미지 가져오기
                ItemImage image1 = imageRepository.findByItemNo_noAndFilenameNotLikeOrderByNoAsc(itemno, "%상세%");
                // 대표이미지가 있으면 새이미지로 수정
                if(image1 != null){
                    log.info("이미지 33 => {}" , image1.getFilename());
                    image1.setFilesize( BigDecimal.valueOf( file.getSize() ) );
                    image1.setFiledata( file.getInputStream().readAllBytes() );
                    image1.setFiletype( file.getContentType() );
                    image1.setFilename( file.getOriginalFilename() );
                    Item obj1 = new Item();
                    obj1.setNo(itemno);
                    image1.setItemNo( obj1 );
                    imageRepository.save(image1);
                }
                // 대표이미지가 없으면 새이미지 등록
                else {
                    ItemImage image2 = new ItemImage();
                    image2.setFilesize( BigDecimal.valueOf( file.getSize()) );
                    image2.setFiledata( file.getInputStream().readAllBytes() );
                    image2.setFiletype( file.getContentType() );
                    image2.setFilename( file.getOriginalFilename() );
                    Item obj1 = new Item();
                    obj1.setNo(itemno);
                    image2.setItemNo( obj1 );
                    imageRepository.save(image2);
                }
    
            }
            // 상세이미지 등록
            else if(image==1){
                for(MultipartFile file1 : files){
                    ItemImage images = new ItemImage();
                    images.setFilesize( BigDecimal.valueOf(file1.getSize()) );
                    images.setFiledata( file1.getInputStream().readAllBytes() );
                    images.setFiletype( file1.getContentType() );
                    images.setFilename( file1.getOriginalFilename() + "_상세");

                    Item obj1 = new Item();
                    obj1.setNo(itemno);
                    images.setItemNo( obj1 );

                    imageRepository.save(images);
                }
            }

            return "redirect:/seller/itemimage/selectlist.do?no=" + itemno;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }   

    /* =============================이미지삭제================================= */ 
    @PostMapping(value = "/deleteimage.do")
    public String deleteImagePOST(@ModelAttribute ItemImage image, @RequestParam(name = "item.no")BigDecimal itemno, @RequestParam(name = "imageno") long[] imageno){
        try {
            // ItemImage itemimage = new ItemImage();
            // imageRepository.deleteAllByNo(imageno);
            int ret = imageMapper.deleteImageBatch(imageno);
            log.info("ret=>{}", ret);
            return "redirect:/seller/itemimage/selectlist.do?no=" + itemno.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }
    }


    /* =============================등록된이미지(대표, 상세) 조회================================= */ 
    @GetMapping(value = "/selectlist.do")
    public String updateimageGET(Model model, HttpServletRequest request, @RequestParam(name = "no")long no){
        try {
            Item item = iRepository.findById(BigDecimal.valueOf(no)).orElse(null);
            
            // 대표이미지
            // String a = "%상세%";

            ItemImage image = imageRepository.findByItemNo_noAndFilenameNotLikeOrderByNoAsc(BigDecimal.valueOf(no), "%상세%");
            item.setImageUrl( request.getContextPath() + "/seller/itemimage/image?no=" + 0);
            if(image != null){
                item.setImageUrl( request.getContextPath() + "/seller/itemimage/image?no=" + image.getNo().longValue() );
                log.info("image {}", image.toString());
            }
            model.addAttribute("item", item);
            
            // 전체이미지
            // List<String> 타입으로 만들어서 view로 전달후 출력
            List<ItemImage> list = imageRepository.findByItemNo_noAndFilenameLikeOrderByNoAsc(BigDecimal.valueOf(no), "%상세%");
            if( !list.isEmpty() ){
                log.info("imagelist => {}", list.toString());
            }

            List<String[]> imageList = new ArrayList<>();
            for( ItemImage one : list){
                String [] str = new String[2];
                // 이미지번호
                str[0]= one.getNo().longValue()+""; 
                // 이미지Url
                str[1]= request.getContextPath() + "/seller/itemimage/image?no=" + one.getNo().longValue();
                imageList.add(str);
            }
            model.addAttribute("imageList", imageList);
            
            // ItemImage image = imageRepository.
            log.info("itemno => {}", no);

            return "/mj/seller/itemimage/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/home.do";
        }

    }

    
    
}
