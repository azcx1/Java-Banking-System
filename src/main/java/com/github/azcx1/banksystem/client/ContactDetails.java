package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.client.Address;
import com.github.azcx1.banksystem.common.model.client.EmailAddress;
import com.github.azcx1.banksystem.common.model.client.PhoneNumber;

public class ContactDetails {
    private Address address;
    private EmailAddress emailAddress;
    private PhoneNumber phoneNumber;

    public ContactDetails(Address address, EmailAddress email, PhoneNumber number) {
        setAddress(address);
        setEmailAddress(email);
        setPhoneNumber(number);
    }

    public void setAddress(Address address) {
        if ( address == null )
            throw new IllegalArgumentException("Address cannot be null");
        this.address = address;
    }
    public Address getAddress() {
        return this.address;
    }

    public void setEmailAddress(EmailAddress email) {
        if ( email == null)
            throw new IllegalArgumentException("Email address cannot be null");
        this.emailAddress = email;
    }
    public EmailAddress getEmailAddress() {
        return this.emailAddress;
    }

    public void setPhoneNumber(PhoneNumber number) {
        if ( number == null)
            throw new IllegalArgumentException("Phone number cannot be null");
        this.phoneNumber = number;
    }
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
