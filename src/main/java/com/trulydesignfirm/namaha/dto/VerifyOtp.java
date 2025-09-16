package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.annotation.Mobile;
import jakarta.validation.constraints.NotBlank;

public record VerifyOtp(

        @NotBlank(message = "Mobile number is required!")
        @Mobile(message = "Invalid mobile number format!")
        String mobile,

        @NotBlank(message = "Otp is required")
        String otp
) {}