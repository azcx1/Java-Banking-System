package com.github.azcx1.banksystem.account;

import com.github.azcx1.banksystem.client.Client;

import java.util.Currency;

public class StandardAccount extends BankAccount {
    public StandardAccount(Client owner, Currency currency){
        super(owner, currency);
    }
}
