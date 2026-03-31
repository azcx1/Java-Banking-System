package com.github.azcx1.banksystem;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.account.SavingsAccount;
import com.github.azcx1.banksystem.account.StandardAccount;
import com.github.azcx1.banksystem.client.Client;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;
import com.github.azcx1.banksystem.utils.AccountRepository;
import com.github.azcx1.banksystem.utils.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Currency;

public class BankService {
    private final BankAccountRepository accountRepository;

    public BankService(){
        this.accountRepository = new AccountRepository();
    }

    public void transfer(AccountNumber fromNumber, AccountNumber toNumber, BigDecimal amount) {
        if ( amount.compareTo(BigDecimal.ZERO) <= 0 )
            throw new IllegalArgumentException("Transfer amount must be positive");

        BankAccount sender = accountRepository.findByNumber(fromNumber)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
        BankAccount receiver = accountRepository.findByNumber(toNumber)
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        sender.withdraw(amount);
        receiver.deposit(amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
    public void transfer(AccountNumber from, AccountNumber to, double amount){
        transfer(from, to, BigDecimal.valueOf(amount));
    }

    public void deposit(String accountNumber, double amount) {
        BankAccount account = accountRepository.findByNumber(new AccountNumber(accountNumber))
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, double amount) {
        BankAccount account = accountRepository.findByNumber(new AccountNumber(accountNumber))
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.withdraw(amount);
    }

    public BankAccount openStandardAccount(Client owner, String currencyCode) {
        if ( currencyCode == null || currencyCode.trim().isBlank())
            throw new IllegalArgumentException("Currency Symbol can not be empty");
        currencyCode = currencyCode.trim();

        BankAccount account = new StandardAccount(
                owner,
                Currency.getInstance(currencyCode)
        );
        accountRepository.save(account);
        owner.addBankAccount(account.getAccountNumber(), account);
        return account;
    }

    public BankAccount openSavingsAccount(Client owner, String currencyCode) {
        if ( currencyCode == null || currencyCode.trim().isBlank())
            throw new IllegalArgumentException("Currency Symbol can not be empty");
        currencyCode = currencyCode.trim();

        BankAccount account = new SavingsAccount(
                owner,
                Currency.getInstance(currencyCode),
                BigDecimal.valueOf(0.05)
        );
        accountRepository.save(account);
        owner.addBankAccount(account.getAccountNumber(), account);
        return account;
    }
}
