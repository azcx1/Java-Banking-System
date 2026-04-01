package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.client.Address;
import com.github.azcx1.banksystem.common.model.client.EmailAddress;
import com.github.azcx1.banksystem.common.model.client.PhoneNumber;

import java.util.Objects;

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
        this.address = Objects.requireNonNull(address, "Address object cannot be null");
    }
    public Address getAddress() {
        return this.address;
    }

    public void setEmailAddress(EmailAddress email) {
        this.emailAddress = Objects.requireNonNull(email, "Email object cannot be null");
    }
    public EmailAddress getEmailAddress() {
        return this.emailAddress;
    }

    public void setPhoneNumber(PhoneNumber number) {
        this.phoneNumber = Objects.requireNonNull(number, "Phone number object cannot be null");
    }
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
