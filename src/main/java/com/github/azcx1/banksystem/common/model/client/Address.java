package com.github.azcx1.banksystem.common.model.client;

import java.util.regex.Pattern;

public class Address {
    private String city;
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String flatNumber;

    private static final  Pattern postalCodePattern = Pattern.compile( "^\\d{2}-\\d{3}$" );

    public Address( String city, String street, String postalCode, String buildingNumber, String flatNumber ) {
        setCity(city);
        setStreet(street);
        setPostalCode(postalCode);
        setBuildingNumber(buildingNumber);
        setFlatNumber(flatNumber);
    }
    public Address( String city, String street, String postalCode, String buildingNumber ){
        setCity(city);
        setStreet(street);
        setPostalCode(postalCode);
        setBuildingNumber(buildingNumber);
    }

    public void setCity( String city ) {
        if ( city == null || city.isBlank() )
            throw new IllegalArgumentException("City can not be empty");
        this.city = city;
    }
    public String getCity() {
        return this.city;
    }

    public void setStreet( String street ) {
        if ( street == null || street.isBlank() )
            throw new IllegalArgumentException("street can not be empty");
        this.street = street;
    }
    public String getStreet() {
        return this.street;
    }

    public void setPostalCode( String postalCode ) {
        if ( postalCode == null || postalCode.isBlank() )
            throw new IllegalArgumentException("Postal code can not be empty");
        if( !postalCodePattern.matcher(postalCode).matches() )
            throw new IllegalArgumentException("Incorrect postal code format");
        this.postalCode = postalCode;
    }
    public String getPostalCode() {
        return this.postalCode;
    }

    public void setBuildingNumber( String number ) {
        if( number == null || number.isBlank() )
            throw new IllegalArgumentException("number can not be empty");
        this.buildingNumber = number;
    }
    public String getBuildingNumber() {
        return this.buildingNumber;
    }

    public void setFlatNumber( String number ) {
        this.flatNumber = number;
    }
    public String getFlatNumber() {
        return this.flatNumber;
    }

    @Override
    public String toString() {
        if ( flatNumber == null || flatNumber.isBlank() )
            return String.format("%s(%s), %s %s", getCity(), getPostalCode(), getStreet(), getBuildingNumber());
        return String.format("%s(%s), %s %s / %s", getCity(), getPostalCode(), getStreet(), getBuildingNumber(), getFlatNumber());
    }
}
