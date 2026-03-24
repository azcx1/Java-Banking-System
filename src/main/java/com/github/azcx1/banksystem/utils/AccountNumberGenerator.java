package com.github.azcx1.banksystem.utils;

import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.util.concurrent.atomic.AtomicLong;

public class AccountNumberGenerator {
    private static final AtomicLong counter = new AtomicLong(1000000000000000L);
    private static final String BANK_ROUTING_NUMBER = "58575100";

    public static AccountNumber generateNext(){
        long nextCustomerNumber = counter.getAndIncrement();

        String controlDigits = "12";
        String fullNUmber = controlDigits + BANK_ROUTING_NUMBER + nextCustomerNumber;

        return new AccountNumber(fullNUmber);
    }
}
