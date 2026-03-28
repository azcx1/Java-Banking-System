package com.github.azcx1.banksystem.utils;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.util.Optional;

public interface BankAccountRepository {
    void save(BankAccount account);
    Optional<BankAccount> findByNumber(AccountNumber accountNumber);
}
