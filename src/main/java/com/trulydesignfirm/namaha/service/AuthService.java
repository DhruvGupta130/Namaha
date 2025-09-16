package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.OtpRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.VerifyOtp;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    Response getOtp(@Valid OtpRequest request);

    Response verifyOtp(@Valid VerifyOtp request);
}