package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.client.Address;
import com.github.azcx1.banksystem.common.model.client.Nip;
import com.github.azcx1.banksystem.common.model.client.Regon;

public class CorporateCustomer extends Client{
    private String companyName;
    private Nip nip;
    private Regon regon;


    public CorporateCustomer
            (String companyName, String nip, String regon, String email, String phoneNumber, String city, String street, String postalCode, String buildingNumber, String flatNumber){
        super(email, phoneNumber, new Address(city, street, postalCode, buildingNumber, flatNumber ));
        setCompanyName(companyName);
        setNip(nip);
        setRegon(regon);
    }
    public CorporateCustomer
            (String companyName, String nip, String regon, String email, String phoneNumber, String city, String street, String postalCode, String buildingNumber){
        this(companyName, regon, nip, email, phoneNumber, city, street, postalCode, buildingNumber, null);
    }

    public void setCompanyName(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("Name can not be empty");
        this.companyName = name;
    }
    public String getCompanyName() {
        return this.companyName;
    }

    public void setNip(String nip) {
        this.nip = new Nip(nip);
    }
    public Nip getNip(){
        return this.nip;
    }

    public void setRegon(String regon) {
        this.regon = new Regon(regon);
    }
    public Regon getRegon() {
        return this.regon;
    }
}
