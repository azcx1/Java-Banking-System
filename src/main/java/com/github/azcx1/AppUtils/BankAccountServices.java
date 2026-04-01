package com.github.azcx1.AppUtils;

import java.util.Currency;
import java.util.Scanner;

public class BankAccountServices {
    private final Scanner scanner;

    public BankAccountServices(Scanner scanner){
        this.scanner = scanner;
    }

    public Currency chooseAccountCurrency(){
        System.out.println("Choose account currency");
        System.out.println("1. PLN");
        System.out.println("2. USD");
        System.out.println("3. EUR");
        System.out.println("4. Back to main menu");

        String choice = scanner.nextLine();

        return switch (choice){
            case "1" -> Currency.getInstance("PLN");
            case "2" -> Currency.getInstance("USD");
            case "3" -> Currency.getInstance("EUR");
            default -> throw new IllegalArgumentException("Incorrect number");
        };
    }
}
