package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.util.Map;
import java.util.UUID;

public interface Client {
    UUID getId();
    ContactDetails getContactDetails();
    void addBankAccount(AccountNumber number, BankAccount account);
    Map<AccountNumber, BankAccount> getBankAccounts();
}
