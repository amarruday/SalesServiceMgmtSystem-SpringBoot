package com.yashsales.config;

import com.yashsales.exceptions.ExceptionInformation;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfiguration {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionInformation> handleForbiddenException(AccessDeniedException ade) {
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .code("FORBIDDEN_ACCESS")
                .message(ade.getMessage())
                .build();
        return new ResponseEntity<>(exceptionInformation, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
