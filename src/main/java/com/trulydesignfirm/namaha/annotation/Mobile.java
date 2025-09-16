package com.trulydesignfirm.namaha.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "Mobile number is required!")
@Pattern(
        regexp = "^\\+[1-9][0-9]{7,14}$",
        message = "Mobile number must include country code (e.g., +919876543210)"
)
public @interface Mobile {

    String message() default "Invalid mobile number!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}