package com.yashsales.exceptions;

import com.yashsales.dto.commons.BrandDto;

public class BrandAlreadyExistsException extends RuntimeException {
    public BrandAlreadyExistsException(String msg) {
        super(msg);
    }
}
