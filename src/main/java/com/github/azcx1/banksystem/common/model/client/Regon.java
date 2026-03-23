package com.github.azcx1.banksystem.common.model.client;

import java.util.regex.Pattern;

public record Regon(String regon) {
    private static final Pattern REGON_PATTERN = Pattern.compile("^(\\d{9}|\\d{14})$");
    private static final int[] WEIGHTS_9 = {8, 9, 2, 3, 4, 5, 6, 7};
    private static final int[] WEIGHTS_14 = {2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8};

    public Regon{
        if ( regon == null || regon.isBlank() )
            throw new IllegalArgumentException("Regon number can not be empty");
        regon = regon.trim();

        if ( !REGON_PATTERN.matcher(regon).matches() )
            throw new IllegalArgumentException("Incorrect regon format");
        if ( !isValid(regon) )
            throw new IllegalArgumentException("Invalid Regon checksum (control digit)");
    }

    public static boolean isValid(String regon) {
        int length = regon.length();

        int[] weights = (length == 9) ? WEIGHTS_9 : WEIGHTS_14;

        int sum = 0;
        for(int i = 0; i < weights.length - 1; i++) {
            sum += Character.getNumericValue(regon.charAt(i)) * weights[i];
        }
        int calculatedControlDigit = (sum % 11) % 10;
        int actualControlDigit = Character.getNumericValue(regon.charAt(length - 1));

        return calculatedControlDigit == actualControlDigit;
    }
}
