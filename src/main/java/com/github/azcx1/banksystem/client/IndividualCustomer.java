package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;
import com.github.azcx1.banksystem.common.model.client.Pesel;
import com.github.azcx1.person.Person;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IndividualCustomer
        extends Person
        implements  Client {

    private final UUID id;
    private final ContactDetails contactDetails;
    private final Map<AccountNumber, BankAccount> bankAccounts;

    public IndividualCustomer(Pesel pesel, String firstName, String lastName, ContactDetails contactDetails){
        super(pesel, firstName, lastName);
        this.id = UUID.randomUUID();
        this.contactDetails = contactDetails;
        this.bankAccounts = new HashMap<>();
    }


    @Override
    public UUID getId(){
        return id;
    }
    @Override
    public ContactDetails getContactDetails(){
        return contactDetails;
    }

    @Override
    public void addBankAccount(AccountNumber number, BankAccount account) {
        if ( number == null )
            throw new IllegalArgumentException("number can not be null");
        if ( account == null )
            throw new IllegalArgumentException("account can not be null");
        bankAccounts.put(number, account);
    }
    @Override
    public Map<AccountNumber, BankAccount> getBankAccounts() {
        return Collections.unmodifiableMap(bankAccounts);
    }
}
