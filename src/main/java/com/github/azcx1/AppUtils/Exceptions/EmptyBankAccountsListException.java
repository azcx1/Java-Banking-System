package com.github.azcx1.AppUtils.Exceptions;

public class EmptyBankAccountsListException extends RuntimeException{
    public EmptyBankAccountsListException(String message){
        super(message);
    }
}
