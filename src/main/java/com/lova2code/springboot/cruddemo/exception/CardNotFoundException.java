package com.lova2code.springboot.cruddemo.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }

}
