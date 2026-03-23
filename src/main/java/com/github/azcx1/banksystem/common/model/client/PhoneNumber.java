package com.github.azcx1.banksystem.common.model.client;

import java.util.regex.Pattern;

public record PhoneNumber(String phoneNumber) {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+\\d{2})?\\d{9}$");

    public PhoneNumber{
        if ( phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Phone number can not be null");
        if(!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches())
            throw new IllegalArgumentException("Incorrect phone number format");
    }
}
