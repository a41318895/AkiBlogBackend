package com.akichou.service.impl;

import com.akichou.service.MailService;
import com.akichou.util.NonceGenerator;
import com.akichou.util.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.akichou.constant.SystemConstants.FORGOT_PASSWORD_VERIFY_CODE_REDIS_KEY;

/**
 * Mail Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/26 Fri.
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final RedisCache redisCache ;

    @Override
    public void sendMailForForgettingPassword(String receiverEmail) {

        String numberUsedOnceCode = NonceGenerator.generateNonce();
        redisCache.setCacheObject(FORGOT_PASSWORD_VERIFY_CODE_REDIS_KEY, numberUsedOnceCode, 2, TimeUnit.MINUTES) ;

        final String subject = "Forgot Password Info Verified Successfully" ;
        String content = "Your Verification Code is : [ " + numberUsedOnceCode + " ]\n\n Please key this into 'Verification Code Space' to verify identification in 2 minutes !" ;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
