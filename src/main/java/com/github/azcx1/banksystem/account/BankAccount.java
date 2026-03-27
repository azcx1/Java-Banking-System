package com.github.azcx1.banksystem.account;

import com.github.azcx1.banksystem.common.model.account.AccountNumber;
import com.github.azcx1.banksystem.utils.AccountNumberGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public abstract class BankAccount {
    private final AccountNumber accountNumber;
    private BigDecimal accountBalance = BigDecimal.ZERO;
    private final Currency accountCurrency;
    private final OClient owner;
    private List<OClient> coOwners;
    private final LocalDateTime creationDate;

    public BankAccount(OClient owner, Currency currency){
        accountNumber = AccountNumberGenerator.generateNext();
        this.owner = owner;
        this.creationDate = LocalDateTime.now();
        this.accountCurrency = currency != null ? currency : Currency.getInstance("PLN");
    }

    public void deposit(BigDecimal amount) {
        if ( amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Deposit value can not be equal/lower than 0");
        this.accountBalance = this.accountBalance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Withdraw value can not be equal/lower than 0");
        if (this.accountBalance.compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");
        this.accountBalance = this.accountBalance.subtract(amount);
    }

    public BigDecimal getAccountBalance(){
        return this.accountBalance;
    }

    public AccountNumber getAccountNumber(){
        return this.accountNumber;
    }

    public Currency getAccountCurrency(){
        return this.accountCurrency;
    }

    public OClient getOwner(){
        return this.owner;
    }

    public void addCoOwner(OClient person) {
        if ( person == null )
            throw new IllegalArgumentException("Object person can not be null");
        if ( this.coOwners == null )
            this.coOwners = new ArrayList<>();
        this.coOwners.add(person);
    }

    public void removeCoOwner(UUID id){
        if ( id == null )
            throw new IllegalArgumentException("id can not be empty");
        if (this.coOwners == null || this.coOwners.isEmpty() )
            return;

        boolean isRemoved = this.coOwners.removeIf(person -> person.getId().equals(id));
        if (!isRemoved )
            throw new IllegalArgumentException("Co owner not found");
    }

    public List<OClient> getCoOwners(){
        if ( this.coOwners == null )
            return Collections.emptyList();
        return Collections.unmodifiableList(this.coOwners);
    }

    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }
}
