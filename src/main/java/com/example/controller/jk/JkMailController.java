package com.example.controller.jk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.EmailMessage;
import com.example.service.jk.JkMailService;


@RestController
@RequestMapping("/mail")
public class JkMailController {

    @Autowired
    private JkMailService mailService;


    // http://127.0.0.1:5959/SOBUN/mail/send?email=junkue13@gmail.com
    @GetMapping("/send")
    public EmailMessage sendPwMail(String email, String newPassword) {
        EmailMessage sendEmail = new EmailMessage();

        sendEmail.setAddress(email);
        sendEmail.setTitle("[소분소분] - 새로운 비밀번호로 로그인해주세요.");
        sendEmail.setMessage("고객님의 새로 발급된 임시비밀번호는 "  + "[" + newPassword + "]" + " 입니다.\n로그인 후 비밀번호를 반드시 변경해주세요." );


        mailService.sendMail(sendEmail);

        return sendEmail;
    }
}