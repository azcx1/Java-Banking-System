package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.Address;
import com.github.azcx1.banksystem.common.model.EmailAddress;
import com.github.azcx1.banksystem.common.model.PhoneNumber;

import java.util.Objects;
import java.util.UUID;

public abstract class Client {
    private final UUID id;
    private EmailAddress email;
    private PhoneNumber phoneNumber;
    protected Address address;


    public Client(String email, String phoneNumber, Address address){
        this.id = UUID.randomUUID();
        setEmailAddress(new EmailAddress(email));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        setAddress(address);
    }

    public UUID getId() {
        return this.id;
    }

    public void setEmailAddress(EmailAddress email) {
        this.email = Objects.requireNonNull(email, "Email address can not be null");
    }
    public EmailAddress getEmailAddress() {
        return this.email;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number con not be null");
    }
    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = Objects.requireNonNull(address, "Address can not be null");
    }
    public Address getAddress() {
        return this.address;
    }
}
