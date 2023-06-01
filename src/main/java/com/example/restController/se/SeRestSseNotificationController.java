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

    // 클라이언트가 전송을 했을 때 수행 (테스트용)
    @GetMapping(value="/sse/notify/publish")
    public void publish( @RequestParam(name = "message") String message ) {
        
        // map 에서 보관된 개수만큼 반복하면서 키값을 꺼냄
        for(String id : clients.keySet()){

            try {
                // map 의 키를 이용해서 value 값을 꺼냄
                SseEmitter emitter = clients.get(id);

                // 클라이언트로 메시지 전송
                emitter.send(message, MediaType.APPLICATION_JSON); // import org.springframework.http.MediaType;

            } 
            catch (Exception e) {
                clients.remove(id); // 오류 생기면 map 에서 제거
            }

        }
    }

    
    // 찜한 물품의 공구가 열렸을 때
    // message
    @GetMapping(value="/sse/jjim/publish")
    public void jjimPublish( @RequestParam(name = "message") String message,
     @RequestParam(name = "itemno", required = false) BigDecimal itemno ) { // required = false 지우기

        try {
            
            // 1) DB 에서 공구 열린 물품을 찜하고 있는 사람을 모두 찾아서 알림 테이블에 insert 실행하기
            List<SeJjimProjection> list = cService.findByItemEntity_no(BigDecimal.valueOf(13L));
            
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
                noti.setUrl("/SOBUN/customer/item/selectone.do?itemno=" + 13);

                int ret = cService.saveCNotification(noti);
                log.info("찜공구알림insert => {}", ret);

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 결제한 공구의 인원이 모두 찼을 때
    @GetMapping(value="/sse/finishpurchase/publish")
    public void finishpurchasejjimPublish( @RequestParam(name = "message") String message ) {

        // 1) DB 에서 해당 공구에 참여중인 사람을 모두 찾아서 알림 테이블에 insert 실행하기

        // 2) 해당 공구에 참여중인 사람 중에 clients 에 저장된 사람한테 알림기능 보내기
        
        // map 에서 보관된 개수만큼 반복하면서 키값을 꺼냄
        for(String id : clients.keySet()){
            try {
                

                // map 의 키를 이용해서 value 값을 꺼냄
                SseEmitter emitter = clients.get(id);
                // 클라이언트로 메시지 전송
                emitter.send(message, MediaType.APPLICATION_JSON); // import org.springframework.http.MediaType;

            } 
            catch (Exception e) {
                clients.remove(id); // 오류 생기면 map 에서 제거
            }

        }
    }
    
    

    
}
