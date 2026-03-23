package com.github.azcx1.banksystem.account;

import com.github.azcx1.banksystem.client.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

public abstract class BankAccount {
    private String accountNumber;
    private BigDecimal accountBalance;
    private Currency accountCurrency;
    private Client owner;
    private List<Client> coOwners;
    private LocalDateTime creationDate;

    public BankAccount(){

    }
}
