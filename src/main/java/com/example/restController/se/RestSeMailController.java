package com.example.restController.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.SendMail;
import com.example.service.se.SeMailService;

@RestController
@RequestMapping("/mail")
public class RestSeMailController {

    @Autowired
    private SeMailService mailService;

    @GetMapping("/send")
    public void sendTestMail(String email) {
        SendMail mail = new SendMail();

        mail.setAddress(email);
        mail.setTitle("밤둘레 님이 발송한 이메일입니다.");
        mail.setMessage("안녕하세요. 반가워요!");

        mailService.sendMail(mail);

        // return mail;
    }

}
