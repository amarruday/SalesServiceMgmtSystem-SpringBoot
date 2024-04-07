package com.yashsales.exceptions;

public class ActionTypeAlreadyExists extends RuntimeException {
    public ActionTypeAlreadyExists(String msg) {
        super(msg);
    }
}
