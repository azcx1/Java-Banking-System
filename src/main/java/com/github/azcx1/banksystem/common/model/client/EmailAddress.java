package com.github.azcx1.banksystem.common.model.client;

import java.util.regex.Pattern;

public record EmailAddress(String email) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile( "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public EmailAddress{
        if ( email == null || email.isBlank() )
            throw new IllegalArgumentException("Email can not be empty");
        if ( !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Incorrect email format");
    }
}
