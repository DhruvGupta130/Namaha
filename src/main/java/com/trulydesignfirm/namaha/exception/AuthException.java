package com.trulydesignfirm.namaha.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    public AuthException(String errorMessage) {
        super(errorMessage);
    }

    public AuthException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}
