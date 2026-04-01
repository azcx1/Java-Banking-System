package com.github.azcx1.banksystem.account;

import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record SingleTransaction(String to, BigDecimal amount, LocalDateTime dateTime) {
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDate = dateTime.format(formatter);
        return String.format("%s | %s | %s", amount.toString(), to, formattedDate);
    }
}
