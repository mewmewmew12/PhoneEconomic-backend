package org.example.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    public void sendEmail(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage(); // tạo ra đối tưởng Mail đơn giản
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

//    public void sendFile(String email, String subject, String content,byte[] files) {
//        MimeMessage message =  javaMailSender.createMimeMessage();
//        MimeMessageHelper hepler;
//        try{
//            hepler = new MimeMessageHelper(message,true);
//            hepler.setTo(email);
//            hepler.setSubject(subject);
//            hepler.setText(content);
//            ByteArrayDataSource fileExport = new ByteArrayDataSource(files,"application/pdf");
//            hepler.addAttachment("Bill.pdf",fileExport);
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
