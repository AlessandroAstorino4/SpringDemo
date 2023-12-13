package com.lova2code.springboot.cruddemo.exception;

public class CardsListEmptyException extends RuntimeException{

    public CardsListEmptyException(String message) { super(message); }
}
