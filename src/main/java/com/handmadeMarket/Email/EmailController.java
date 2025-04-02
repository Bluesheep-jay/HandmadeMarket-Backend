package com.handmadeMarket.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    // API gửi OTP
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        emailService.sendOtpEmail(email);
        return ResponseEntity.ok("OTP đã được gửi đến email của bạn!");
    }

    // API xác nhận OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
        if (emailService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("Xác thực thành công!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ hoặc đã hết hạn!");
    }
}
