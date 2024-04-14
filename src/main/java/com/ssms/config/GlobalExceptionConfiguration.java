package com.ssms.config;

import com.ssms.constants.ApplicationConstants;
import com.ssms.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfiguration {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionInformation> handleForbiddenException(AccessDeniedException ade) {
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .code("FORBIDDEN_ACCESS")
                .message(ade.getMessage())
                .status(ApplicationConstants.ResponseConstants.RESPONSE_FAILURE)
                .build();
        return new ResponseEntity<>(exceptionInformation, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ActionTypeAlreadyExists.class)
    public ResponseEntity<ExceptionInformation> handleActionTypeAlreadyExistsException(ActionTypeAlreadyExists actionTypeAlreadyExists) {
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .code("ACTION_TYPE_EXISTS")
                .message(actionTypeAlreadyExists.getMessage())
                .status(ApplicationConstants.ResponseConstants.RESPONSE_FAILURE)
                .build();
        return new ResponseEntity<>(exceptionInformation, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CommonReplyAlreadyExistsException.class)
    public ResponseEntity<ExceptionInformation> handleCommonReplyAlreadyExistsException(CommonReplyAlreadyExistsException commonReplyAlreadyExistsException) {
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .code("COMMON_REPLY_EXISTS")
                .message(commonReplyAlreadyExistsException.getMessage())
                .status(ApplicationConstants.ResponseConstants.RESPONSE_FAILURE)
                .build();
        return new ResponseEntity<>(exceptionInformation, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EnquirySourceAlreadyExists.class)
    public ResponseEntity<ExceptionInformation> handleEnquirySourceAlreadyExistsException(EnquirySourceAlreadyExists enquirySourceAlreadyExists) {
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .code("ENQ-001")
                .message(enquirySourceAlreadyExists.getMessage())
                .status(ApplicationConstants.ResponseConstants.RESPONSE_FAILURE)
                .build();
        return new ResponseEntity<>(exceptionInformation, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ExceptionInformation> handleBrandAlreadyExistsException(BrandAlreadyExistsException brandAlreadyExistsException){
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .code("BR-001")
                .message(brandAlreadyExistsException.getMessage())
                .status(ApplicationConstants.ResponseConstants.RESPONSE_FAILURE)
                .build();
        return new ResponseEntity<>(exceptionInformation, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
