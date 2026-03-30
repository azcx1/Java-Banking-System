package com.github.azcx1.AppUtils;

import java.util.Scanner;

public class BankAccountServices {
    private final Scanner scanner;

    public BankAccountServices(Scanner scanner){
        this.scanner = scanner;
    }

    public String chooseAccountCurrency(){
        System.out.println("Choose account currency");
        System.out.println("1. PLN");
        System.out.println("2. USD");
        System.out.println("3. EUR");
        System.out.println("4. Back to main menu");

        String choice = scanner.nextLine();

        return switch (choice){
            case "1" -> "PLN";
            case "2" -> "USD";
            case "3" -> "EUR";
            default -> throw new IllegalArgumentException("Incorrect number");
        };
    }
}
