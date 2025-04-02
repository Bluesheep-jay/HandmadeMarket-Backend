package com.handmadeMarket.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void sendOtpEmail(String email) {
        String otp = generateOtp();
        saveOtpToRedis(email, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Xác thực tài khoản");
        message.setText("Mã xác thực của bạn là: " + otp + "\nMã này sẽ hết hạn sau 5 phút.");

        mailSender.send(message);
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // Tạo mã 6 số
    }

    private void saveOtpToRedis(String email, String otp) {
        redisTemplate.opsForValue().set("OTP_" + email, otp, 5, TimeUnit.MINUTES); // Lưu OTP trong 5 phút
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get("OTP_" + email);
        return otp.equals(storedOtp);
    }
}