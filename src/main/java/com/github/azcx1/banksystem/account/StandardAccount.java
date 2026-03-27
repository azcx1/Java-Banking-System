package com.github.azcx1.banksystem.account;

import java.util.Currency;

public class StandardAccount extends BankAccount{
    public StandardAccount(OClient owner, Currency currency){
        super(owner, currency);
    }
}
