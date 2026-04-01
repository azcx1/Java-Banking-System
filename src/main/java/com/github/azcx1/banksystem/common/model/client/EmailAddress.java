package com.github.azcx1.banksystem.common.model.client;

import java.util.regex.Pattern;

public record EmailAddress(String email) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public EmailAddress {
        if ( email == null || email.isBlank() )
            throw new IllegalArgumentException("Email can not be empty");

        email = email.trim().toLowerCase();

        if ( !EMAIL_PATTERN.matcher(email).matches() )
            throw new IllegalArgumentException("Incorrect email format");
    }
}
