package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.annotations.Mobile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(

        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email format!")
        String email,

        @NotBlank(message = "Mobile number is required!")
        @Mobile(message = "Invalid mobile number format!")
        String mobile,

        @NotBlank(message = "Name is required!")
        String name,

        @NotBlank(message = "Password is required!")
        String password
) {
}