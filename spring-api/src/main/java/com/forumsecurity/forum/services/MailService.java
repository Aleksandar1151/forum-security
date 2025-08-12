package com.forumsecurity.forum.services;

import com.forumsecurity.forum.models.MailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MailService {

    private ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();
    @Autowired
    private JavaMailSender mailSender;


    @Value("$(spring.mail.username)")
    private String fromMail;
    public void sendWelcomeMail(String mail, MailStructure mailStructure)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.subject);
        simpleMailMessage.setText(mailStructure.message);
        simpleMailMessage.setTo(mail);

        mailSender.send(simpleMailMessage);

    }

    public void sendVerificationCode(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);
        mailSender.send(message);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a 6-digit code
        return String.valueOf(code);
    }

    public void saveVerificationCode(String username, String code) {
        verificationCodes.put(username, code);
    }

    public boolean verifyCode(String username, String code) {
        String savedCode = verificationCodes.get(username);
        return savedCode != null && savedCode.equals(code);
    }

    public void removeCode(String username) {
        verificationCodes.remove(username);
    }
}
