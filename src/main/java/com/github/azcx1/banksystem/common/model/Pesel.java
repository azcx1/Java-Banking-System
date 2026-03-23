package com.github.azcx1.banksystem.common.model;

import java.util.regex.Pattern;

public class Pesel {
    private String peselNumber;

    private final static Pattern peselPattern = Pattern.compile("^\\d{11}$");

    public Pesel(String peselNumber){
        setPesel(peselNumber);
    }

    private void setPesel(String pesel) {
        if ( pesel == null || pesel.isBlank() )
            throw new IllegalArgumentException("Pesel can not be empty");
        if ( !peselPattern.matcher(pesel).matches() )
            throw new IllegalArgumentException("Incorrect pesel pattern");
        this.peselNumber = pesel;
    }
    public String getPesel(){
        return this.peselNumber;
    }

}
