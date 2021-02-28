package com.youngchayoungcha.tastynote.service;

import com.youngchayoungcha.tastynote.web.dto.MailCertifiedDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "jhcheon2134@gmail.com";

    public void sendEmail(MailCertifiedDTO mailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getAddress());
        message.setFrom(EmailService.FROM_ADDRESS);
        message.setSubject(mailDTO.getTitle());
        message.setText("인증키 : " + mailDTO.getCertifiedKey());

        javaMailSender.send(message);
    }
}
