package com.trulydesignfirm.namaha.dto;

import org.springframework.http.HttpStatus;

public record Response(
        String message,
        HttpStatus status,
        Object data
) {}