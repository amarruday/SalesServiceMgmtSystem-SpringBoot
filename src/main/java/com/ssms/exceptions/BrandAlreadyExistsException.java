package com.ssms.exceptions;

public class BrandAlreadyExistsException extends RuntimeException {
    public BrandAlreadyExistsException(String msg) {
        super(msg);
    }
}
