package com.github.azcx1.banksystem.common.model;

import java.util.regex.Pattern;

public class PhoneNumber {
    private String phoneNumber;

    private static final Pattern phoneNumberPattern = Pattern.compile("^(\\+\\d{2})?\\d{9}$");

    public PhoneNumber(String number){
        setPhoneNumber(number);
    }

    public void setPhoneNumber(String phoneNumber){
        if ( phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Phone number can not be null");
        if(!phoneNumberPattern.matcher(phoneNumber).matches())
            throw new IllegalArgumentException("Incorrect phone number format");
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
}
