package com.github.azcx1.person;

import com.github.azcx1.banksystem.common.model.client.Pesel;

import java.time.LocalDate;

public abstract class Person {
    private final Pesel pesel;
    private String firstName;
    private String lastName;

    public Person(Pesel pesel, String firstName, String lastName) {
        this.pesel = pesel;
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Pesel getPesel() {
        return this.pesel;
    }

    public void setFirstName(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("name can not be empty");
        this.firstName = name;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String name) {
        name = name.trim();
        if ( name.isBlank() )
            throw new IllegalArgumentException("name can not be empty");
        this.lastName = name;
    }
    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return pesel.getBirthDate();
    }
    public Pesel.Gender getGender() {
        return pesel.getGender();
    }

    @Override
    public String toString() {
        return String.format("%s %s {Pesel: %s, Birth date: %s, Gender: %s}", getFirstName(), getLastName(),
                getPesel().toString(), getBirthDate(), getGender());
    }

    @Override
    public boolean equals(Object o){
        if ( this == o )
            return true;
        if ( o == null || this.getClass() != o.getClass() )
            return false;
        Person person = (Person) o;
        return pesel.equals(person.pesel);
    }
    @Override
    public int hashCode(){
        return pesel.hashCode();
    }
}
