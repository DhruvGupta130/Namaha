package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.annotations.Mobile;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Mobile number is required")
        @Mobile(message = "Invalid mobile number")
        String mobile,

        @NotBlank(message = "Password is required")
        String password
) {
}
