package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.common.model.Address;
import com.github.azcx1.banksystem.common.model.Pesel;

public class IndividualCustomer extends Client {
    private String firstName;
    private String lastName;
    private final Pesel clientPesel;

    public IndividualCustomer
            ( String firstName, String lastName, String peselNumber, String email, String phoneNumber, String city,
              String street, String postalCode, String buildingNumber, String flatNumber) {
        super(email, phoneNumber, new Address(city, street, postalCode, buildingNumber, flatNumber ));

        setFirstName(firstName);
        setLastName(lastName);

        this.clientPesel = new Pesel(peselNumber);
    }
    public IndividualCustomer
            ( String firstName, String lastName, String peselNumber, String email, String phoneNumber, String city,
              String street, String postalCode, String buildingNumber) {

        this(firstName, lastName, peselNumber, email, phoneNumber, city, street, postalCode, buildingNumber, null);
    }

    public void setFirstName(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("First name can not be empty");
        this.firstName = name;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("Last name can not be empty");
        this.lastName = name;
    }
    public String getLastName() {
        return this.lastName;
    }

    public Pesel getClientPesel(){
        return this.clientPesel;
    }
}
