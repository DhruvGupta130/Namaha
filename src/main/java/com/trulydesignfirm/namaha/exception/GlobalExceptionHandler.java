package com.trulydesignfirm.namaha.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found", ex);
        return getErrorResponse(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("Unexpected runtime error", ex);
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Runtime Error", "Something went wrong.");
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(AuthException ex) {
        log.warn("Bad credentials attempt", ex);
        return getErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication Failed", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument provided", ex);
        return getErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request", "Invalid request data.");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        log.error("IO Exception", ex);
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "IO Error", "Internal processing error.");
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        log.error("User Exception", ex);
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "User Error", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation failed", ex);
        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return getErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", errorMessages);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorResponse> handleTransactionSystemException(TransactionSystemException ex) {
        log.error("Transaction system exception", ex);
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof ConstraintViolationException constraintViolationException) {
            return handleConstraintViolationException(constraintViolationException);
        }
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction Error", "Transaction failed. Please try again.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("Constraint violation", ex);
        String errorMessages = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        return getErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", errorMessages);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(HttpStatus status, String errorType, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(LocalDateTime.now(), status, errorType, message));
    }
}