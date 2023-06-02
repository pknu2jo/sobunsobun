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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Customer;
import com.example.dto.CustomerUser;
import com.example.dto.KmOrderSuccess;
import com.example.dto.Storage;
import com.example.dto.kmPurchaseView;
import com.example.entity.ItemImage;
import com.example.entity.ReviewEntity;
import com.example.entity.ReviewImageEntity;
import com.example.entity.km.KmReviewNoProjection;
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
    @Value("${review.pagetotal}") int PAGETOTAL;

    // ------------------------------------------------------------------------------
    // 물품 상세 조회
    @GetMapping(value = "/item/selectone.do")
    public String selectitemGET(@RequestParam(name = "itemno") BigDecimal no,
                                @RequestParam(name="tab", defaultValue = "detail") String tab,
                                @RequestParam(name="orderby", defaultValue = "", required = false) String orderby,
                                @RequestParam(name="page", defaultValue = "0", required = false) int page,
                                @AuthenticationPrincipal CustomerUser user,
                                HttpServletRequest request,
                                Model model ) {
        // log.info("물품 상세 조회 GET 진입");

        try {

            // review 영역의 pagination 처리
            // if(page == 0) {
            //     System.out.println("안되네....어쩌지");
            //     return "redirect:/customer/item/selectone.do?itemno=" + no + "&tab=review&page=1";
            // }

            long itemno = Long.valueOf(no.toPlainString());

            // log.info("user 정보 보기 => {}", user);

            // 물품 정보 가져오기
            Map<String, Object> item = customerService.selectOneItem(itemno);
            // log.info("item check 확인 => {}", item.toString());
            // itemView => {SELLERNAME=LG생활건강, ITEMPRICE=8.97E+4, SCATEGORYNAME=세탁세제, ITEMNO=11,
            //              SCATEGORYCODE=132, MCATEGORYNAME=세제/청소/주방세제, LCATEGORYNAME=생활용품, ITEMNAME=액체형세제 2.8L 6개}

            // 상품 번호에 해당하는 이미지 번호
            List<Long> imgList = customerService.selectItemImageNoList(itemno);
            // log.info("imgList 테스트 => {}", imgList);

            // 찜 여부 가져오기
            if( user != null) { // 로그인 안된 상태
                // itemno, id에 해당하는 찜 여부 가져오기
                int jjim = customerService.checkJjim(user.getUsername(), no);
                model.addAttribute("jjim", jjim);
            } else if(user == null) {
                model.addAttribute("jjim", 0);
            }

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


            long total = customerService.countByItemEntity_no(no);

            if (tab.equals("review")) {
                // 리뷰 탭을 보여줄 로직

                // 리뷰 전체 목록 가져오기
                // List<ReviewEntity> reviewList = customerService.findByItemEntity_noOrderByNoDesc(no, page);
                List<ReviewEntity> reviewList = customerService.findByItemEntity_noOrderByNoDesc(no, page);
                // log.info("total count review => {} ", total);

                if ( reviewList != null ) {
                    // log.info("review List 조회1 => {}", reviewList.toString());

                    for(ReviewEntity review : reviewList) {
                        List<KmReviewNoProjection> reviewImgNoList = customerService.selectReviewImageNoList(review.getNo());
    
                        if(!reviewImgNoList.isEmpty()) {
                            review.setImgUrl1(request.getContextPath() + "/customer/kmreviewimage?no=" + reviewImgNoList.get(0).getNo());
                            if(reviewImgNoList.size() == 2) {
                                review.setImgUrl2(request.getContextPath() + "/customer/kmreviewimage?no=" + reviewImgNoList.get(1).getNo());
                            }
                        }
                    }
                } else {
                    log.info("review List nullnullnull");
                }

                if(orderby.equals("rating")) {
                    reviewList = customerService.findByItemEntity_noOrderByRatingDescNoDesc(no, page);
                    log.info("왜 이게 안되냐고 => {}", reviewList.toString());
                }
                
                int pageSize = 2; // 한페이지에 나오는 아이템갯수
                // int start1 = (page -1) * pageSize;
                // int end1 = page * pageSize;

                int totalPages = (int) ((total-1) / PAGETOTAL) + 1;
                int currentSet = (int) Math.ceil((double) page/5);
                int startPage = (currentSet - 1) * 5 + 1;
                int endPage = Math.min(startPage + 4, totalPages);

                model.addAttribute("reviewList", reviewList);
                model.addAttribute("pages", totalPages);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("currentPage", page);

            }
    

            

            model.addAttribute("purchaseList", purchaseList);
            model.addAttribute("item", item);
            model.addAttribute("imgList", imgList);
            model.addAttribute("storage", storage);
            model.addAttribute("user", user);

            // log.info("보관소 정보 storage => {}", storage.toString());
            // log.info("purchaseList => {}", purchaseList);
            // log.info("itemView 확인  => {}", item);

            return "/km/customer/selectitem";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }

    }

    // /item/order.do로 보내기 위한 과정
    @PostMapping(value = "/item/selectone1.do")
    public String selectitemPOST(@ModelAttribute kmPurchaseView obj, Model model) {
        try {
            // log.info("post view 확인1 => {}", obj.toString());
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
            // log.info("order.do storage 확인 => {}", storage.toString());
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
            // log.info("user 정보 확인 => {}", user.toString());
            // log.info("customer 확인 => {}", customer.toString());
            
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
            // log.info("phoneAndEmail => {}", phoneAndEmail); // {email2=naver.com, email1=a, phone3=5678, phone2=1234, phone1=010}

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


    final ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}")
    String DEFAULTIMAGE;

    // 물품 이미지
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") BigDecimal no)
            throws IOException {

        try {
            ItemImage obj = customerService.findItemImageById(no);
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

    // 리뷰 이미지
    @GetMapping(value = "/kmreviewimage")
    public ResponseEntity<byte[]> reviewImage(@RequestParam(name = "no", defaultValue = "0") BigDecimal no)
            throws IOException {

        try {
            ReviewImageEntity obj = customerService.findReviewImageById(no);
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

    // ------------------------------------------------------

    // 세션 저장용
    @PostMapping(value = "/ordersuccess1.do")
    public String orderSuccessPOST(@ModelAttribute KmOrderSuccess obj, Model model) {
        try {
            httpSession.setAttribute("kmOrderSuccess", obj);

            // log.info("세션에 저장될 ordersuccess => {}", obj.toString());
            // log.info("세션에 저장한 ordersuccess => {}", httpSession.getAttribute("kmOrderSuccess"));

            return "redirect:/customer/ordersuccess.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customer/home.do";
        }
    }

    @GetMapping(value = "/ordersuccess.do")
    public String orderSuccessGET(Model model,
                    @AuthenticationPrincipal CustomerUser user,
                    HttpServletRequest request) {
        try {
            // 세션에서 가져오기
            KmOrderSuccess obj = (KmOrderSuccess) httpSession.getAttribute("kmOrderSuccess");

            // log.info("세션에서 가져오기 => {}", obj.toString());
            // KmOrderSuccess(itemname=수건 10개, orderno=km060269, totalprice=30,699원, 
                // storagename=부산강서구점, orderdate=2023.06.02, purchaseno=1073)
            model.addAttribute("user", user);
            model.addAttribute("ordersuccess", obj);
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
