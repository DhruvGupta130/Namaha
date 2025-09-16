package com.trulydesignfirm.namaha.service;

import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    int generateOtp(String mobile);

    boolean verifyOtp(String mobile, String otp);
}
