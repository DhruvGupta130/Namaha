package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.annotation.Mobile;

public record OtpRequest(
        @Mobile(message = "Invalid mobile number format!")
        String mobile
) {}
