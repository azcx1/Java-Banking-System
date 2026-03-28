package com.github.azcx1.banksystem.utils;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountRepository implements BankAccountRepository{

    private final Map<AccountNumber, BankAccount> accounts = new HashMap<>();

    @Override
    public void save(BankAccount account){
        if( account == null )
            throw new IllegalArgumentException("Cannot save a null account");
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public Optional<BankAccount> findByNumber(AccountNumber accountNumber){
        if ( accountNumber == null)
            return Optional.empty();
        return Optional.ofNullable(accounts.get(accountNumber));
    }
}
