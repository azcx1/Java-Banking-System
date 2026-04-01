package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.util.*;

public abstract class Client {
    private final UUID id;
    private ContactDetails contactDetails;

    private final Map<AccountNumber, BankAccount> accounts;

    public Client(ContactDetails contactDetails) {
        this.id = UUID.randomUUID();
        this.contactDetails = Objects.requireNonNull(contactDetails, "ContactDetails object cannot be null");
        accounts = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void addAccount(BankAccount account) {
        Objects.requireNonNull(account, "Bank account object cannot be null");

        this.accounts.put(account.getAccountNumber(), account);
    }

    public Map<AccountNumber, BankAccount> getBankAccounts() {
        return Collections.unmodifiableMap(this.accounts);
    }

    public abstract String getDisplayName();

    @Override
    public boolean equals(Object o) {
        if ( this == o )
            return true;
        if ( o == null || this.getClass() != o.getClass() )
            return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
