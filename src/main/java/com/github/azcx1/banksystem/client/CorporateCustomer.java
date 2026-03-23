package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.Address;

public class CorporateCustomer extends Client{
    private String companyName;
    private String nip;
    private String regon;


    public CorporateCustomer
            (String email, String phoneNumber, String city, String street, String postalCode, String buildingNumber, String flatNumber){
        super(email, phoneNumber, new Address(city, street, postalCode, buildingNumber, flatNumber ));
    }
    public CorporateCustomer
            (String email, String phoneNumber, String city, String street, String postalCode, String buildingNumber){
        super(email, phoneNumber, new Address(city, street, postalCode, buildingNumber ));
    }
}
