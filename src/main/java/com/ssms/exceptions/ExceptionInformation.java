package com.ssms.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionInformation {
    private String code;
    private String message;
    private String status;
}
