package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.client.Pesel;
import com.github.azcx1.person.Person;

import java.util.UUID;

public class IndividualCustomer
        extends Person
        implements  Client {

    private final UUID id;
    private final ContactDetails contactDetails;

    public IndividualCustomer(Pesel pesel, String firstName, String lastName, ContactDetails contactDetails){
        super(pesel, firstName, lastName);
        this.id = UUID.randomUUID();
        this.contactDetails = contactDetails;
    }


    @Override
    public UUID getId(){
        return id;
    }
    @Override
    public ContactDetails getContactDetails(){
        return contactDetails;
    }
}
