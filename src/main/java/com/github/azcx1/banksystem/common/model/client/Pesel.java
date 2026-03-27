package com.github.azcx1.banksystem.common.model.client;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public record Pesel(String peselNumber) {
    private final static Pattern peselPattern = Pattern.compile("^\\d{11}$");
    private static final int[] WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    public enum Gender {
        MALE,
        FEMALE
    }

    public Pesel {
        if ( peselNumber == null || peselNumber.isBlank() )
            throw new IllegalArgumentException("Pesel can not be empty");
        if ( !peselPattern.matcher(peselNumber).matches() )
            throw new IllegalArgumentException("Incorrect pesel pattern");
        if ( !isValidChecksum(peselNumber) )
            throw new IllegalArgumentException("Invalid checksum (control digit)");

        try {
            calculateBirthDate(peselNumber);
        } catch ( DateTimeException | IllegalArgumentException e ) {
            throw new IllegalArgumentException("Invalid birth date encoded is pesel", e);
        }
    }

    public LocalDate getBirthDate(){
        return calculateBirthDate(this.peselNumber);
    }

    private static LocalDate calculateBirthDate(String peselStr) {
        int year = Integer.parseInt(peselStr.substring(0,2));
        int month = Integer.parseInt(peselStr.substring(2,4));
        int day = Integer.parseInt(peselStr.substring(4,6));

        if (month >= 1 && month <= 12) {
            year += 1900;
        } else if (month >= 21 && month <= 32) {
            year += 2000;
            month -= 20;
        } else if (month >= 41 && month <= 52) {
            year += 2100;
            month -= 40;
        } else if (month >= 61 && month <= 72) {
            year += 2200;
            month -= 60;
        } else if (month >= 81 && month <= 92) {
            year += 1800;
            month -= 80;
        } else {
            throw new IllegalArgumentException("Invalid month encoded in PESEL");
        }

        return LocalDate.of(year, month, day);
    }

    public Gender getGender() {
        int genderDigit = Character.getNumericValue(peselNumber.charAt(9));
        return (genderDigit % 2 == 0) ? Gender.FEMALE : Gender.MALE;
    }

    private static boolean isValidChecksum(String pesel) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(pesel.charAt(i)) * WEIGHTS[i];
        }
        int calculatedControlDigit = (10 - (sum % 10)) % 10;
        int actualControlDigit = Character.getNumericValue(pesel.charAt(10));

        return calculatedControlDigit == actualControlDigit;
    }

    @Override
    public String toString() {
        return this.peselNumber;
    }
}
