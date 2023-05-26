package com.example.controller.km;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Customer;
import com.example.dto.CustomerUser;
import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;
import com.example.entity.ItemImage;
import com.example.service.km.KmCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class KmCustomerContorller {

    final KmCustomerService customerService;
    final HttpSession httpSession; // 정보 전달용 session 객체 생성

    // ------------------------------------------------------------------------------
    // 물품 상세 조회
    @GetMapping(value = "/item/selectone.do")
    public String selectitemGET(@RequestParam(name = "itemno") long itemno,
                                @AuthenticationPrincipal CustomerUser user,
                                Model model ) {
        // @RequestParam(name = "no") long no 로 itemno 받기

        // long no = 11; // 물품 번호 (공구 열린거)
        // long no = 13; // 물품 번호 (공구 안열린거)

        log.info("물품 상세 조회 GET");

        try {

            log.info("user 정보 보기 => {}", user);

            // 물품 정보 가져오기
            Map<String, Object> item = customerService.selectOneItem(itemno);
            // itemView => {SELLERNAME=LG생활건강, ITEMPRICE=8.97E+4, SCATEGORYNAME=세탁세제, ITEMNO=11,
            //              SCATEGORYCODE=132, MCATEGORYNAME=세제/청소/주방세제, LCATEGORYNAME=생활용품, ITEMNAME=액체형세제 2.8L 6개}

            // 상품 번호에 해당하는 이미지 번호
            List<Long> imgList = customerService.selectItemImageNoList(itemno);

            // 상품에 대한 열린 공구 가져오기 -> 남은 인원
            List<kmPurchaseView> purchaseList = customerService.selectPurchaseList(itemno);
            for (Iterator<kmPurchaseView> it = purchaseList.iterator(); it.hasNext();) {
                kmPurchaseView obj = it.next();

                long remainingPerson = customerService.countRemainingPerson(obj.getPurchaseNo());
                obj.setRemainingPerson(remainingPerson);

                if (obj.getRemainingPerson() <= 0L) {
                    it.remove();
                }
            }

            // 열린 공구들에 참가 중인 고객의 id list 가져오기
            for (kmPurchaseView obj : purchaseList) {
                obj.setMemIdList( customerService.selectIdList( obj.getPurchaseNo() ) );
            }

            // 보관소 지점 가져오기
            List<Storage> storage = customerService.selectStorageList();
            // Storage(no=4, name=부산중구점, phone=051-600-4000, phostcode=null, address1=부산광역시 중구 중구로 120, address2=(대청동1가, 중구청), 
            //          address3=null, latitude=35.1062826, longitude=129.032355, adminId=admin, regdate=Thu May 18 07:03:58 KST 2023)

            model.addAttribute("purchaseList", purchaseList);
            model.addAttribute("item", item);
            model.addAttribute("imgList", imgList);
            model.addAttribute("storage", storage);
            model.addAttribute("user", user);

            log.info("보관소 정보 storage => {}", storage.toString());
            log.info("purchaseList => {}", purchaseList);
            log.info("itemView  => {}", item);

            return "/km/customer/selectitem";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }

    }

    @PostMapping(value = "/item/selectone.do")
    public String selectitemPOST(@ModelAttribute kmPurchaseView obj, Model model) {
        try {
            log.info("post view 확인1 => {}", obj.toString());
            // 공구 참여 버튼 -> kmPurchaseView(no=0, purchaseNo=1007, participant=0,
            //                                  deadline=null, remainingPerson=0, itemNo=0, itemName=null,
            //                                  itemPrice=0, storageNo=0, storageName=null, pricePerOne=0, imageUrl=null)
            // => purchaseNo만 넘어옴

            // 공구 개설 버튼 -> kmPurchaseView(no=0, purchaseNo=0, participant=2, deadline=null,
            //                                  remainingPerson=0, itemNo=11, itemName=null,
            //                                  itemPrice=0, storageNo=4, storageName=null, pricePerOne=0, imageUrl=null)
            // => participate, itemNo, storageNo 넘어옴

            httpSession.setAttribute("kmPurchaseView", obj);

            return "redirect:/customer/item/order.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }

    // ---------------------------------------------------------------------------------

    @GetMapping(value = "/item/order.do")
    public String orderGET(Model model, HttpServletRequest request, @AuthenticationPrincipal CustomerUser user) {
        try {
            // 세션에서 가져오기
            kmPurchaseView obj = (kmPurchaseView) httpSession.getAttribute("kmPurchaseView");

            // log.info("user information 확인 => {}", user.toString());

            if (obj.getPurchaseNo() != 0) {
                // 공구 참여

                // 공구 정보 가져오기
                obj = customerService.selectOnePurchase(obj.getPurchaseNo());
            } else if (obj.getPurchaseNo() == 0) {
                // 공구 개설

                // 물품 정보 가져오기
                Map<String, Object> item = customerService.selectOneItem(obj.getItemNo());
                obj.setItemPrice(((BigDecimal) item.get("ITEMPRICE")).longValue());
                obj.setItemName(item.get("ITEMNAME").toString());
                // itemNo = ((BigDecimal) item.get("ITEMNO")).longValue();
                // obj.setStorageName(customerService.selectOneStorage(obj.getStorageNo()));
            }
            obj.setPricePerOne(Math.round(obj.getItemPrice() / obj.getParticipant()));
            // log.info("order.do의 itemNo => {}", obj.getItemNo());

            // 보관소 정보 불러오기
            Storage storage = customerService.selectOneStorage(obj.getStorageNo());
            log.info("order.do storage 확인 => {}", storage.toString());
            // Storage(no=4, name=부산중구점, phone=051-600-4000, postcode=48926, address1=부산광역시 중구 중구로 120, address2=(대청동1가, 중구청), 
            //          address3=null, latitude=35.1062826, longitude=129.032355, adminId=admin, regdate=Thu May 18 07:03:58 KST 2023)


            // 대표 이미지 띄우기
            List<ItemImage> img = customerService.findByItemNo_noOrderByNoAsc(BigDecimal.valueOf(obj.getItemNo()));
            obj.setImageUrl(request.getContextPath() + "/customer/image?no=0");
            if (img != null) {
                obj.setImageUrl(request.getContextPath() + "/customer/image?no=" + img.get(0).getNo());
            }

            // log.info("결제하기 GET => {}", obj.toString());
                // 참여 => kmPurchaseView(no=100, purchaseNo=1016, participant=4, deadline=Fri May 26 05:41:28 KST 2023, remainingPerson=0, itemNo=11, 
                //                         itemName=액체형 세제 2.8L 6개, itemPrice=89700, storageNo=4, storageName=부산중구점, pricePerOne=22425,
                //                         imageUrl=/SOBUN/customer/image?no=9)
                // 개설 => kmPurchaseView(no=0, purchaseNo=0, participant=3, deadline=null, remainingPerson=0, itemNo=11, 
                //                          itemName=액체형 세제 2.8L 6개, itemPrice=89700, storageNo=3, storageName=null, pricePerOne=29900,
                //                          imageUrl=/SOBUN/customer/image?no=9)


            // user의 id로 정보 꺼내오기 (id, name, phone, email)
            Customer customer = customerService.selectOneCustomer(user.getId());
            log.info("user 정보 확인 => {}", user.toString());
            log.info("customer 확인 => {}", customer.toString());
            
            // 연락처, 이메일 잘라서 보내기
            Map<String, String> phoneAndEmail = new HashMap<>();
            if(customer.getPhone().length() == 11) {
                phoneAndEmail.put("phone1", customer.getPhone().substring(0, 3));
                phoneAndEmail.put("phone2", customer.getPhone().substring(3, 7));
                phoneAndEmail.put("phone3", customer.getPhone().substring(7));

            } else if (customer.getPhone().length() == 10) {
                phoneAndEmail.put("phone1", customer.getPhone().substring(0, 3));
                phoneAndEmail.put("phone2", customer.getPhone().substring(3, 6));
                phoneAndEmail.put("phone3", customer.getPhone().substring(6));
            }
            String[] email = customer.getEmail().split("@");
            for(int i=0; i < email.length; i++) {
                phoneAndEmail.put("email" + (i+1), email[i]);
            }
            log.info("phoneAndEmail => {}", phoneAndEmail); // {email2=naver.com, email1=a, phone3=5678, phone2=1234, phone1=010}

            // 화면에 데이터 전달하기
            model.addAttribute("obj", obj);
            model.addAttribute("customer", customer);
            model.addAttribute("phoneAndEmail", phoneAndEmail);
            model.addAttribute("storage", storage);
            model.addAttribute("user", user);

            return "/km/customer/checkout";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }

    // 이미지
    final ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}")
    String DEFAULTIMAGE;

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") BigDecimal no)
            throws IOException {

        try {
            ItemImage obj = customerService.findById(no);
            HttpHeaders headers = new HttpHeaders();

            if (obj != null) {
                if (obj.getFilesize().longValue() > 0L) {
                    headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));

                    return new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);
                }
            }

            InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }


    @GetMapping(value = "/ordersuccess.do")
    public String orderSuccessGET(Model model,
                    @AuthenticationPrincipal CustomerUser user) {
        try {
            
            model.addAttribute("user", user);
            return "/km/customer/ordersuccess";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }

    @GetMapping(value = "/kmtest.do")
    public String testGET(Model model) {
        log.info("푸터 인클루드 테스트");

        // int person = cMapper.countRemainingPerson(1004);
        // log.info("person => {}", person);
        // model.addAttribute("person", person);

        // long no = 5;
        // List<Map<String, Object>> purchaseList =
        // customerService.selectPurchaseList(no);

        // for(Map<String, Object> obj : purchaseList) {
        // // log.info("maplist => {}", obj.get("PURCHASENO"));
        // obj.put("REMAININGPERSON", customerService.countRemainingPerson((BigDecimal)
        // obj.get("PURCHASENO")));
        // // obj.setRemaingPerson(customerService.countRemainingPerson(obj.getNo()));
        // }
        // log.info("purchaseList => {}", purchaseList);

        // model.addAttribute("list", purchaseList);
        return "/km/customer/sample";
    }

}
