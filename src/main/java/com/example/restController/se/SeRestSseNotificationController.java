package com.example.restController.se;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.entity.CNotificationEntity;
import com.example.entity.CustomerEntity;
import com.example.entity.se.SeJjimProjection;
import com.example.entity.se.SePurchaseStatusProjection;
import com.example.service.se.SeCustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api")
@Slf4j
@RequiredArgsConstructor
public class SeRestSseNotificationController {

    final SeCustomerService cService;

    private static final Map<String, SseEmitter> clients = new HashMap<>();


    // 로그인하면 clients 변수에 저장 (알림구독)
    @GetMapping(value="/sse/notify/subscribe")
    public SseEmitter subscribe( @RequestParam(name = "id") String id ) { // 1) 클라이언트 아이디를 받아서
        // log.info("알림 구독 => {}", id);
        // sse 객체 생성
        SseEmitter emitter = new SseEmitter( 3600000L ); // 1000L * 1200 => 20분 동안 접속 유지
        clients.put(id, emitter); // 2) 광역변수 clients 에 넣기(Map 타입) => Map<클라이언트 아이디, sse 객체>
        // Map 에서 키가 중복되면 최신 데이터로 덮어 씌워짐 => 즉 key가 될 id 는 고유해야함

        // 클라이언트 연결 중지 및 완료되면 clients 변수에서 정보 제거
        emitter.onTimeout(() -> clients.remove(id)); // 시간이 만료로 연결 중지 시 clients 정보 제거
        emitter.onCompletion(() -> clients.remove(id));

        return emitter;
    }

    // -------------------------------------------------------------------------------------------------------------------------------
    // 찜한 물품의 공구가 열렸을 때
    @GetMapping(value="/sse/jjim/publish")
    public void jjimPublish( 
        @RequestParam(name = "message") String message,
        @RequestParam(name = "itemno") BigDecimal itemno ) {
        try {
            // log.info("찜알림 아이템번호 => {}", itemno);
            
            // 1) DB 에서 공구 열린 물품을 찜하고 있는 사람을 모두 찾아서 알림 테이블에 insert 실행하기
            List<SeJjimProjection> list = cService.findByItemEntity_no(itemno);
            
            for(SeJjimProjection one : list) {
                String dbId = one.getCustomerEntity().getId();
                // log.info("찜한 회원 => {}", dbId);

                // DB 알림테이블에 등록하기
                CustomerEntity customer = new CustomerEntity();
                customer.setId(dbId);
                CNotificationEntity noti = new CNotificationEntity();
                noti.setCustomerEntity(customer);
                noti.setType("jjim");
                noti.setContent(message);
                noti.setUrl("/SOBUN/customer/item/selectone.do?itemno=" + itemno);

                int ret = cService.saveCNotification(noti);
                // log.info("찜공구알림 insert => {}", ret);
                if(ret == 1){ // DB 에 insert 된 경우만 알림 보내기
                    // 2) 1)에 해당하는 사람 중에 clients 에 저장된 사람한테 알림기능 보내기
                    for(String id : clients.keySet()) {
                        if(id.equals(dbId)){
                            try {
                                SseEmitter emitter = clients.get(id);
                                emitter.send(message, MediaType.APPLICATION_JSON);
                            } catch (Exception e) {
                                clients.remove(id);
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------------
    // 결제한 공구의 인원이 모두 찼을 때
    @GetMapping(value="/sse/completepurchase/publish")
    public void finishpurchasejjimPublish(
        @RequestParam(name = "message") String message,
        @RequestParam(name = "purchaseno") long purchaseno ) {
            // log.info("확인용 => {}, {}", message, purchaseno);

            // 공구 번호를 넣으면 번호가 일치하고 상태가 1인 list 를 가져오기
            // list 가 isEmpty 면 상태가 0 이라는 뜻 => 공구 마감 X
            List<SePurchaseStatusProjection> list = cService.findByPurchaseEntity_NoAndState(BigDecimal.valueOf(purchaseno));
            if(!list.isEmpty()) { // 공구가 마감이 됐다면(상태가 1이라면)
                for (SePurchaseStatusProjection one : list) {
                    String dbId = one.getCustomerEntity().getId();
                    log.info("공구마감 알림을 보낼 아이디 => {}", dbId);
                    // DB 에 알림 isnert
                    CustomerEntity customer = new CustomerEntity();
                    customer.setId(dbId);

                    CNotificationEntity noti = new CNotificationEntity();
                    noti.setCustomerEntity(customer);
                    noti.setType("complete");
                    noti.setContent(message);
                    noti.setUrl("/SOBUN/customer/item/myorderlist.do?page=1");

                    int ret = cService.saveCNotification(noti);
                    log.info("공구마감알림 insert => {}", ret);
                    if(ret == 1){ // DB 에 insert 된 경우만 알림 보내기
                        // 2) 1)에 해당하는 사람 중에 clients 에 저장된 사람한테 알림기능 보내기
                        for(String id : clients.keySet()) {
                            if(id.equals(dbId)){
                                try {
                                    SseEmitter emitter = clients.get(id);
                                    emitter.send(message, MediaType.APPLICATION_JSON);
                                } catch (Exception e) {
                                    clients.remove(id);
                                }
                            }
                        }
                    }
                }
            }
            
    }
    
    

    
}
