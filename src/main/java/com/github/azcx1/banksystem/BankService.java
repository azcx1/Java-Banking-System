package com.github.azcx1.banksystem;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.account.SavingsAccount;
import com.github.azcx1.banksystem.account.SingleTransaction;
import com.github.azcx1.banksystem.account.StandardAccount;
import com.github.azcx1.banksystem.client.Client;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;
import com.github.azcx1.banksystem.utils.AccountRepository;
import com.github.azcx1.banksystem.utils.BankAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

public class BankService {
    private final BankAccountRepository accountRepository;
    private final BigDecimal SAVINGS_INTEREST = BigDecimal.valueOf(0.05);

    public BankService() {
        this.accountRepository = new AccountRepository();
    }

    public void deposit(AccountNumber accountNumber, BigDecimal amount) {
        BankAccount account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        account.addTransactionToHistory(
                new SingleTransaction(
                        "Deposit",
                        amount,
                        LocalDateTime.now()
                )
        );
        accountRepository.save(account);
    }

    public void withdraw(AccountNumber accountNumber, BigDecimal amount) {
        BankAccount account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.withdraw(amount);
        account.addTransactionToHistory(
                new SingleTransaction(
                        "Withdraw",
                        amount.multiply(BigDecimal.valueOf(-1)),
                        LocalDateTime.now()
                )
        );
        accountRepository.save(account);
    }

    public void transfer(AccountNumber fromNumber, AccountNumber toNumber, BigDecimal amount) {
        if ( amount.compareTo(BigDecimal.ZERO) <= 0 )
            throw new IllegalArgumentException("Transfer amount must be positive");

        BankAccount sender = accountRepository.findByNumber(fromNumber)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
        BankAccount receiver = accountRepository.findByNumber(toNumber)
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        sender.withdraw(amount);
        sender.addTransactionToHistory(
                new SingleTransaction(
                        receiver.getAccountNumber().toString(),
                        amount.multiply(BigDecimal.valueOf(-1)),
                        LocalDateTime.now()
                )
        );
        receiver.deposit(amount);
        receiver.addTransactionToHistory(
                new SingleTransaction(
                        sender.getAccountNumber().toString(),
                        amount,
                        LocalDateTime.now()
                )
        );
        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    public BankAccount openStandardAccount(Client owner, Currency currency) {
        Objects.requireNonNull(owner, "Client object cannot be null");
        Objects.requireNonNull(currency, "Currency object cannot be null");

        BankAccount account = new StandardAccount(
                owner,
                currency
        );

        accountRepository.save(account);
        owner.addAccount(account);

        return account;
    }

    public BankAccount openSavingsAccount(Client owner, Currency currency) {
        Objects.requireNonNull(owner, "Client object cannot be null");
        Objects.requireNonNull(currency, "Currency object cannot be null");

        BankAccount account = new SavingsAccount(
                owner,
                currency,
                SAVINGS_INTEREST
        );

        accountRepository.save(account);
        owner.addAccount(account);

        return account;
    }
}
