package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.client.Nip;
import com.github.azcx1.banksystem.common.model.client.Regon;

import java.util.Objects;

public class CorporateCustomer extends Client{
    private final Nip nip;
    private final Regon regon;
    private String name;

    public CorporateCustomer(Nip nip, Regon regon, String name,ContactDetails contactDetails) {
        super(contactDetails);
        this.nip = Objects.requireNonNull(nip, "Nip object cannot be null");
        this.regon = Objects.requireNonNull(regon, "Regon object cannot be null");
        setName(name);

    }

    public Nip getNip() {
        return this.nip;
    }

    public Regon getRegon() {
        return this.regon;
    }

    public void setName(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("Name can not be null");
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("%s {Nip: %s, Regon: %s}", name, nip, regon);
    }

    @Override
    public String getDisplayName(){
        return name;
    }
}
