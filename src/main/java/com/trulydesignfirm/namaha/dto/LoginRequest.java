package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.annotations.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Schema(example = "+919876543210", description = "User mobile number with country code")
        @NotBlank(message = "Mobile number is required")
        @Mobile(message = "Invalid mobile number")
        String mobile,

        @NotBlank(message = "Password is required")
        String password
) {
}
