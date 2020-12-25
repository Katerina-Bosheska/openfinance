package com.example.openfinance.service.exception;

import com.example.openfinance.model.Account;

public class AccountException extends Exception {

    private String message;

    public AccountException(String message){
        this.message = message;
    }
}
