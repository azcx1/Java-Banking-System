package com.github.azcx1.banksystem.common.model.account;

import java.util.regex.Pattern;

public record AccountNumber(String number) {
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^\\d{26}$");

    public AccountNumber{
        if ( number == null || number.isBlank() )
            throw new IllegalArgumentException("Account number can not be empty");
        number = number.replace(" ","");

        if ( !ACCOUNT_NUMBER_PATTERN.matcher(number).matches() )
            throw new IllegalArgumentException("Incorrect acount number pattern");
    }

    @Override
    public String toString() {
        return number.replaceAll("(.{2})(.{4})(.{4})(.{4})(.{4})(.{4})(.{4})", "$1 $2 $3 $4 $5 $6 $7");
    }
}
