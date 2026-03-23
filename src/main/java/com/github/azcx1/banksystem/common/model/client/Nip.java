package com.github.azcx1.banksystem.common.model.client;

import java.util.regex.Pattern;

public record Nip (String nip) {
    private final static Pattern NIP_PATTERN = Pattern.compile("^\\d{10}$");
    private final static int[] WEIGHTS = {6, 5, 7, 2, 3, 4, 5, 6, 7};

    public Nip {
        if ( nip == null || nip.isBlank() )
            throw new IllegalArgumentException("Nip number can not be empty");
        nip = nip.replace("-", "").trim();
        if ( !NIP_PATTERN.matcher(nip).matches() )
            throw new IllegalArgumentException("Incorrect nip pattern");
        if ( !isValid(nip) )
            throw new IllegalArgumentException("invalid checksum (control digit)");
    }

    public static boolean isValid(String nip){
        int sum = 0;
        for(int i = 0; i < 9; i++){
            sum += Character.getNumericValue(nip.charAt(i)) * WEIGHTS[i];
        }
        int calculatedControlDigit = sum % 11;

        int actualControlDigit = Character.getNumericValue(nip.charAt(9));
        return calculatedControlDigit == actualControlDigit;
    }
}
