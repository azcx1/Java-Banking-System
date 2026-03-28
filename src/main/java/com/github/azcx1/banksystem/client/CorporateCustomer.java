package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;
import com.github.azcx1.banksystem.common.model.client.Nip;
import com.github.azcx1.banksystem.common.model.client.Regon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CorporateCustomer implements Client{
    private final UUID id;
    private final ContactDetails contactDetails;
    private final Map<AccountNumber, BankAccount> bankAccounts;

    private String companyName;
    private final Nip nip;
    private final Regon regon;

    public CorporateCustomer(String companyName, Nip nip, Regon regon, ContactDetails contactDetails){
        if ( nip == null)
            throw new IllegalArgumentException("Nip cannot be null");
        if ( regon == null)
            throw new IllegalArgumentException("regon cannot be null");

        id = UUID.randomUUID();
        setCompanyName(companyName);
        this.nip = nip;
        this.regon = regon;
        this.contactDetails = contactDetails;
        this.bankAccounts = new HashMap<>();
    }

    public void setCompanyName(String name) {
        if( name == null || name.trim().isBlank() )
            throw new IllegalArgumentException("Name of company can not be empty");
        this.companyName = name.trim();
    }
    public String getCompanyName() {
        return this.companyName;
    }

    public Nip getNip() {
        return this.nip;
    }

    public Regon getRegon() {
        return this.regon;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if ( o == null || this.getClass() != o.getClass() )
            return false;
        CorporateCustomer customer = (CorporateCustomer) o;
        return nip.equals(customer.nip);
    }
    @Override
    public int hashCode(){
        return nip.hashCode();
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
