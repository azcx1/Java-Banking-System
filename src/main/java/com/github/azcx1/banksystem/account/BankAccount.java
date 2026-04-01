package com.github.azcx1.banksystem.account;

import com.github.azcx1.banksystem.client.Client;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;
import com.github.azcx1.banksystem.utils.AccountNumberGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public abstract class BankAccount {
    private final AccountNumber accountNumber;
    private BigDecimal accountBalance = BigDecimal.ZERO;
    private final Currency accountCurrency;
    private final LocalDateTime creationDate;

    private final Client owner;
    private final List<Client> coOwners;
    private final List<SingleTransaction> history = new ArrayList<>();

    public BankAccount(Client owner, Currency currency) {
        this.accountNumber = AccountNumberGenerator.generateNext();
        this.creationDate = LocalDateTime.now();

        this.owner = Objects.requireNonNull(owner, "Client object cannot be null");
        this.coOwners = new ArrayList<>();

        this.accountCurrency = currency != null ? currency : Currency.getInstance("PLN");
    }

    public AccountNumber getAccountNumber() {
        return this.accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }

    public Currency getAccountCurrency() {
        return this.accountCurrency;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public Client getOwner() {
        return this.owner;
    }

    public void addCoOwner(Client client) {
        Objects.requireNonNull(client, "Client object cannot be null");
        if(client.equals(this.owner))
            throw new IllegalArgumentException("Main owner can not be add as co-owner");

        if (this.coOwners.contains(client))
            throw new IllegalArgumentException("This person is already a co-owner");

        this.coOwners.add(client);
    }

    public void removeCoOwner(UUID id) {
        Objects.requireNonNull(id, "UUID object cannot be null");

        if (this.coOwners == null || this.coOwners.isEmpty() )
            return;

        boolean isRemoved = this.coOwners.removeIf(client -> client.getId().equals(id));
        if ( !isRemoved )
            throw new IllegalArgumentException("Co-owner not found");
    }

    public List<Client> getCoOwners() {
        if ( this.coOwners == null )
            return Collections.emptyList();

        return Collections.unmodifiableList(this.coOwners);
    }

    public List<SingleTransaction> getHistory() {
        return Collections.unmodifiableList(history);
    }
    public void addTransactionToHistory(SingleTransaction transaction){
        history.add(Objects.requireNonNull(transaction, "Transaction object cannot be null"));
    }

    public void deposit(BigDecimal amount) {
        if ( amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Deposit value can not be equal/lower than 0");

        this.accountBalance = this.accountBalance.add(amount);
    }
    public void deposit(double amount) {
        deposit(BigDecimal.valueOf(amount));
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Withdraw value can not be equal/lower than 0");
        if (this.accountBalance.compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");
        this.accountBalance = this.accountBalance.subtract(amount);
    }
    public void withdraw(double amount) {
        withdraw(BigDecimal.valueOf(amount));
    }
}
