package com.github.azcx1.banksystem.common.model;

import java.util.regex.Pattern;

public class EmailAddress {
    private String emailAddress;

    private static final Pattern emailPattern = Pattern.compile( "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public EmailAddress(String email){
        setEmail(email);
    }

    public void setEmail(String email){
        if ( email == null || email.isBlank())
            throw new IllegalArgumentException("Email can not be empty");
        if (!emailPattern.matcher(email).matches())
            throw new IllegalArgumentException("Incorrect email format");
        this.emailAddress = email;
    }
    public String getEmail(){
        return this.emailAddress;
    }
}
