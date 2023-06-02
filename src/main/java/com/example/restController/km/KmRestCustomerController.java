package com.example.restController.km;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.CustomerUser;
import com.example.dto.Purchase;
import com.example.dto.PurchaseOrder;
import com.example.dto.PurchaseStatus;
import com.example.entity.Item;
import com.example.entity.JjimEntity;
import com.example.entity.PurchaseOrderEntity;
import com.example.entity.ReviewEntity;
import com.example.entity.ReviewImageEntity;
import com.example.entity.km.KmCheckReviewView;
import com.example.entity.km.KmOrderNoProjection;
import com.example.service.km.KmCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class KmRestCustomerController {
    
    // 나중에 service 연결해주기
    // final KmCustomerMapper mapper;
    final KmCustomerService customerService;

    // 결제(찐) 성공하면 오는 컨트롤러
        // 여기서 공구 번호를 확인해서  (번호=0 => 공구 개설, 번호!=0 => 공구 참여)
        // 공구 참여면 주문, 공구현황만 추가해주고,
        // 공구 개설이면 공구, 주문, 공구현황 3개 추가 
        // 물품 수량도 -1해주기
    @PostMapping(value="/order.json")
    public Map<String, Object> insertPurchasePOST(@RequestBody PurchaseOrder purchaseOrder, 
                                            @AuthenticationPrincipal CustomerUser user) {
        
        Map<String, Object> retMap = new HashMap<>();
        
        try {
            log.info("rest 확인 => {}", purchaseOrder.toString());
            // PurchaseOrder(no=se4517, memId=null, purchaseNo=0, regdate=null, 
            //             totalPrice=30599, participant=3, storageNo=4, itemNo=11)

            long purchaseNo = purchaseOrder.getPurchaseNo();
            int ret1 = 0;

            // 공구 개설하기 (purchaseOrder.getPurchaseNo == 0) 
            if(purchaseNo == 0) {
                
                // 1. SEQ_PURCHASE_NO.NEXTVAL 가져오기
                purchaseNo = customerService.selectSeqPurchaseNo();
                log.info("rest에서 공구 개설 시 purchaseNo 확인 => {}", purchaseNo);

                // 2. purchase 데이터 추가
                    // no(1에서 가져온 값 넣어줌), participant, storageNo
                Purchase purchase = new Purchase();
                purchase.setNo(purchaseNo);
                purchase.setParticipant(purchaseOrder.getParticipant());
                purchase.setStorageNo(purchaseOrder.getStorageNo());

                log.info("rest에서 공구 개설 시 purchase 확인 => {}", purchase.toString());

                ret1 = customerService.insertOnePurchase(purchase);      

            }      

            // 여기부터는 개설, 참여 공통 부분

            // 3. purchaseOrder 데이터 추가
                // no, memId, totalPrice, purchaseNo(1에서 가져온 값 넣어줌)
            purchaseOrder.setMemId(user.getUsername());
            purchaseOrder.setPurchaseNo(purchaseNo);            
            int ret2= customerService.insertOnePurchaseOrder(purchaseOrder);

            // 4. purchaseStatus 데이터 추가
                // memId, itemNo, purchaseNo(1에서 가져온 값 넣어줌)
            PurchaseStatus purchaseStatus = new PurchaseStatus();
            purchaseStatus.setMemId(user.getUsername());
            purchaseStatus.setItemNo(purchaseOrder.getItemNo());
            purchaseStatus.setPurchaseNo(purchaseNo);
            int ret3 = customerService.insertOnePurchaseStatus(purchaseStatus);

            if(purchaseOrder.getPurchaseNo() == 0) { // 공구 개설
                log.info("공구개설 ret 확인 => {} {} {}",ret1, ret2, ret3);
            } else { // 공구 참여
                log.info("공구참여 ret 확인 => {} {}", ret2, ret3);
            }
            
            // 공구의 참여 인원 다 찼는지 체크 해주기
            int remaingPerson = customerService.countRemainingPerson(purchaseNo);
            log.info("rest remainingPerson => {}", remaingPerson);

            int ret4 = 0;
            int ret5 = 0;
            if ( remaingPerson == 0 ) {
                // 공구 참여중인 회원ID 목록 다 불러오기
                List<String> idList =  customerService.selectIdList(purchaseNo);
                log.info("rest idList => {}", idList.toString());

                // 공구 현황 일괄 insert
                List<PurchaseStatus> statusInsertList = new ArrayList<>();
                for(int i=0; i<idList.size(); i++) {
                    PurchaseStatus obj = new PurchaseStatus();
                    obj.setMemId(idList.get(i));
                    obj.setPurchaseNo(purchaseNo);
                    obj.setItemNo(purchaseOrder.getItemNo());
                    statusInsertList.add(obj);
                }
                ret4 = customerService.PurchaseStatusInsertBatch(statusInsertList);
                log.info("rest로 일괄 추가해야할 statusInsertList 확인하기 => {}", statusInsertList.toString());

                // item의 수량 -1 해주기
                ret5 = customerService.updateItemQuantity(purchaseOrder.getItemNo());
            }

            
            log.info("rest로 확인하기 => {}, {}", ret4, ret5);

            if( purchaseNo == 0 ) {
                if (ret1 == 1 && ret2 == 1 && ret3 == 1) {
                    retMap.put("result", 200);
                }
            } else if( purchaseNo != 0 ) {
                if (ret2 == 1 && ret3 == 1) {
                    if(remaingPerson != 0) {
                        retMap.put("result", 200);
                    } else {
                        if(ret4 > 0 && ret5 == 1) {
                            retMap.put("result", 200);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }

    // 찜 기능 구현
    @PostMapping(value = "/jjim.km")
    public Map<String, Object> JjimPost(@RequestBody JjimEntity jjim) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            log.info("testtest => {}", jjim.toString()); // {memid=km2, itemno=13}
            // log.info("testtest1 itemno => {}", jjim.getItemEntity().getNo());
            // log.info("testtest1 memid => {}", jjim.getCustomerEntity().getId());

            String memid = jjim.getCustomerEntity().getId();
            BigDecimal itemno = jjim.getItemEntity().getNo();

            int ret = customerService.checkJjim(memid, itemno);
            log.info("ret check => {}", ret);

            if(ret  == 0) {
                // 찜 안된 상태 => insert 필요
                System.out.println("abcdefg");
                retMap.put("result", customerService.insertJjim(jjim));
                retMap.put("jjimState", 1);
            } else if (ret  == 1) {
                // 찜 된 상태 => delete 필요
                System.out.println("1234567");
                retMap.put("result", customerService.deleteJjim(memid, itemno));
                retMap.put("jjimState", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }

    // 리뷰 등록 전 해야할 일
    @GetMapping(value="/checkReview.json")
    public Map<String, Object> checkReviewGET(@RequestParam(name = "id") String id, 
                                                @RequestParam(name="itemno") long itemno) { 
        Map<String, Object> retMap = new HashMap<>();
        try {
            // log.info("체크체크check1 => {}", id);
            // log.info("체크체크check2 => {}", itemno);

            // 리뷰 등록 전 구매한 상품이 맞는지 확인하기
            List<BigDecimal> purchaseNoList = customerService.selectCheckOrder(itemno, id);
            // log.info("리뷰 확인하기 => {}", purchaseNoList);

            retMap.put("reviewCount", 1);

            // 리뷰 작성 여부 확인하기 (위에서 purchaseNo 받아옴)
            for(BigDecimal purchaseNo : purchaseNoList) {
                KmCheckReviewView obj = customerService.checkReview(id, purchaseNo);
                // log.info("KmCheckReviewView => {}", obj.toString());
                if(obj == null) {
                    retMap.put("reviewCount", 0);
                    KmOrderNoProjection orderNo =  customerService.findByCustomerEntity_idAndPurchaseEntity_no(id, purchaseNo);
                    retMap.put("orderNo", orderNo.getNo());
                    break;
                } else {

                }
            }
            System.out.println("retMap => " + retMap.get("reviewCount"));

            retMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        }
        return retMap;
    }

    // 리뷰 & 리뷰 이미지 등록하기
    @PostMapping(value="/enterreview.json")
    public Map<String, Object> enterReviewPOST (
                        @RequestParam("rating") BigDecimal rating,
                        @RequestParam("comment") String comment,
                        @RequestParam("orderNo") String orderNo,
                        @RequestParam("itemNo") BigDecimal itemNo,
                        @RequestParam(name="file1", required = false) MultipartFile file1,
                        @RequestParam(name="file2", required = false) MultipartFile file2
                        ) {

        Map<String, Object> retMap = new HashMap<>();
        int ret = 0; int ret1 = 0; int ret2 = 0;

        try {
            log.info("enter review Rating => {}", rating);
            log.info("enter review Comment => {}", comment);

            // Review save
            ReviewEntity review = new ReviewEntity();
            review.setComment(comment);
            review.setRating(rating);
                PurchaseOrderEntity purchaseOrder = new PurchaseOrderEntity();
                purchaseOrder.setNo(orderNo);
            review.setPurchaseOrderEntity(purchaseOrder);
                Item item = new Item();
                item.setNo(itemNo);
            review.setItemEntity(item);
            ret = customerService.saveReview(review);


            ReviewEntity review1 = customerService.findByPurchaseOrderEntity_no(orderNo);
            log.info("review1 을 확인 => {} ", review1.toString());

            // ReviewImage save
            if (file1 != null){
                log.info("enter review Files => {}", file1.toString());

                ReviewImageEntity reviewImg1 = new ReviewImageEntity();
                reviewImg1.setReviewno( review1 );
                reviewImg1.setFilesize( BigDecimal.valueOf(file1.getSize()) );
                reviewImg1.setFiledata( file1.getInputStream().readAllBytes() );
                reviewImg1.setFiletype( file1.getContentType() );
                reviewImg1.setFilename( file1.getOriginalFilename() );

                ret1 = customerService.saveReviewImage(reviewImg1);
            }
            if(file2 != null) {
                log.info("enter review Files => {}", file2.toString());

                ReviewImageEntity reviewImg2 = new ReviewImageEntity();
                reviewImg2.setReviewno( review1 );
                reviewImg2.setFilesize( BigDecimal.valueOf(file2.getSize()) );
                reviewImg2.setFiledata( file2.getInputStream().readAllBytes() );
                reviewImg2.setFiletype( file2.getContentType() );
                reviewImg2.setFilename( file2.getOriginalFilename() );

                ret2 = customerService.saveReviewImage(reviewImg2);
            }

            if(file1 == null && ret == 1) {
                // 리뷰 글만 등록함(사진 아무것도 첨부 안함)
                retMap.put("result", 200);
                retMap.put("content", "Review");
            } else if(file1!=null && file2==null && ret==1 && ret1==1) {
                // 리뷰글 등록 + 사진 1개 첨부
                retMap.put("result", 200);
                retMap.put("content", "Review + Image1");
            } else if(file2 != null && ret==1 && ret1==1 && ret2==1) {
                // 리뷰 + 이미지 2개 모두 첨부
                retMap.put("result", 200);
                retMap.put("content", "Review + Image1 + Image2");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", -1);
        } 
        return retMap;
    }

    // // 리뷰 전체 목록 조회하기
    // @GetMapping(value="findreview.json")
    // public Map<String, Object> findReviewGET(@RequestParam(name = "itemno") BigDecimal itemno) {
    //     Map<String, Object> retMap = new HashMap<>();

    //     try {
    //         List<ReviewEntity> obj = customerService.findByItemEntity_noOrderByNoDesc(itemno);
            
    //         // obj를 바로 map에 담아서 보내려고 하니까 그 안에 있는 Entity(item, order, image)때문에 오류 생김
    //         List<ReviewEntity> reviews = new ArrayList<>();

            
    //         for(ReviewEntity ret : obj) {
    //             ReviewEntity review = new ReviewEntity();
    //             review.setNo(ret.getNo());
    //             review.setComment(ret.getComment());
    //             review.setRating(ret.getRating());
    //             review.setRegdate(ret.getRegdate());
    //             // review.setImageList(ret.getImageList());
    //             reviews.add(review);
    //         }
            
    //         log.info("rest reviews 확인하기 => {}", reviews.toString());

    //         retMap.put("reviews", reviews);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         retMap.put("result", -1);
    //     } 
    //     return retMap;
    // }
}
