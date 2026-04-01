package com.github.azcx1.banksystem.client;

import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.common.model.client.Pesel;

import java.util.List;
import java.util.Objects;

public class IndividualClient extends Client {
    private final Pesel pesel;
    private String name;
    private String middleName;
    private String surname;

    public IndividualClient(Pesel pesel, String name, String surname, ContactDetails contactDetails) {
        super(contactDetails);
        this.pesel = Objects.requireNonNull(pesel, "Pesel object cannot be null");
        setName(name);
        setSurname(surname);
    }
    public IndividualClient(Pesel pesel, String name, String middleName, String surname, ContactDetails contactDetails) {
        this(pesel, name, surname, contactDetails);
        setMiddleName(middleName);
    }

    public Pesel getPesel() {
        return this.pesel;
    }

    public void setName(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("Name cannot be null");
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setSurname(String surname) {
        if ( surname == null || surname.isBlank() )
            throw new IllegalArgumentException("Surname cannot be null");
        this.surname = surname;
    }
    public String getSurname() {
        return this.surname;
    }

    public void setMiddleName(String middleName) {
        if ( middleName == null || middleName.isBlank() )
            throw new IllegalArgumentException("Middle name cannot be null");
        this.middleName = middleName;
    }
    public String getMiddleName() {
        return this.middleName;
    }

    @Override
    public String toString() {
        if ( middleName == null )
            return String.format("%s, %s, %s", pesel.toString(), name, surname);
        return String.format("%s, %s, %s, %s", pesel.toString(), name, middleName ,surname);
    }

    @Override
    public String getDisplayName() {
        if ( middleName == null )
            return String.format("%s %s", name, surname);
        return String.format("%s %s %s", name, middleName ,surname);
    }
}
