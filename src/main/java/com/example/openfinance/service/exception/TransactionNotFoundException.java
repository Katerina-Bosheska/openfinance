package com.example.openfinance.service.exception;

public class TransactionNotFoundException extends Exception {

    private String message;

    public TransactionNotFoundException(String message){
        this.message = message;
    }
}
