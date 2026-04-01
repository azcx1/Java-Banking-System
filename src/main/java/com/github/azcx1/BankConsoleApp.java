package com.github.azcx1;

import com.github.azcx1.AppUtils.AccountSummaryDTO;
import com.github.azcx1.AppUtils.ClientService;
import com.github.azcx1.AppUtils.Exceptions.EmptyBankAccountsListException;
import com.github.azcx1.AppUtils.Exceptions.EmptyClientListException;
import com.github.azcx1.banksystem.BankController;
import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.account.SingleTransaction;
import com.github.azcx1.banksystem.client.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BankConsoleApp {
    private final Scanner scanner = new Scanner(System.in);

    BankController controller;
    ClientService clientService;

    public BankConsoleApp() {
        this.controller = new BankController(scanner);
        this.clientService = new ClientService(scanner);
    }
    public void start() {
        while(true) {
            try {
                if ( controller.getCurrentClient() == null )
                    showMainMenu();
                if ( controller.getCurrentBankAccount() == null)
                    clientMenu();
                else
                    bankAccountMenu();

            } catch ( EmptyClientListException e) {
                System.err.println("[!] " + e.getMessage());
            } catch (Exception e) {
                System.err.println("\n[!] Unexpected error: " + e.getMessage());
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=-=-= Main Menu -0-0-0\n");
        System.out.println("1. Select client");
        System.out.println("2. Add client");
        System.out.println("3. Exit");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> clientSelectionMenu();
            case "2" -> chooseClientType();
            case "3" -> System.exit(0);
            default -> System.err.println("Incorrect number");
        }
    }

    private void clientSelectionMenu() {
        List<Client> clients = controller.getClients();

        if ( clients == null || clients.isEmpty() )
            throw new EmptyClientListException("List is empty. Add a client first.");

        System.out.println("\n=-=-= Select client -0-0-0\n");

        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i+1) + ". " + clients.get(i).getDisplayName());
        }
        int lastOption = clients.size() + 1;
        System.out.println(lastOption + ". Back to main menu");
        System.out.print("\nChoice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if ( choice == lastOption )
                return;
            if( choice > 0 && choice <= clients.size() ) {
                controller.setCurrentClient(clients.get(choice-1));
                clientMenu();
            } else {
                System.err.println("Invalid number");
            }
        } catch (NumberFormatException e) {
            System.err.println("Please enter a valid number");
        }
    }

    private void chooseClientType() {
        System.out.println("\n=-=-= Choose client type -0-0-0\n");
        System.out.println("1. Individual customer");
        System.out.println("2. Corporative customer");
        System.out.println("3. Back to main menu");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> controller.addClient( clientService.createIndividualCustomer() );
            case "2" -> controller.addClient( clientService.createCorporateCustomer() );
            case "3" -> {return;}
            default -> System.err.println("Incorrect number");
        }
    }

    private void clientMenu() {
        if ( controller.getCurrentClient() == null )
            return;
        System.out.println("\n=-=-= Client menu -0-0-0\n");
        System.out.println("Hello, " + controller.getCurrentClientName());
        System.out.println("1. Choose bank account");
        System.out.println("2. Create bank account");
        System.out.println("3. Logout");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> bankAccountSelectionMenu();
            case "2" -> chooseBankAccountType();
            case "3" -> {
                controller.setCurrentClient(null);
                return;
            }
            default -> System.err.println("Incorrect number");
        }
    }

    private void bankAccountSelectionMenu() {
        List<BankAccount> clientBankAccounts = controller.getClientBankAccountsToList();
        if ( clientBankAccounts.isEmpty() )
            throw new EmptyBankAccountsListException("List is empty. Create bank account first.");

        System.out.println("\n=-=-= Select bank account -0-0-0\n");

        int size = clientBankAccounts.size();
        for (int i = 0; i < size; i++) {
            BankAccount account = clientBankAccounts.get(i);
            System.out.println( (i+1) + ". " + account.getAccountNumber()
            + " | " + account.getAccountBalance() + " " + account.getAccountCurrency());
        }
        int lastOption = clientBankAccounts.size() + 1;
        System.out.println(lastOption + ". Back to menu");

        System.out.print("\nChoice: ");
        try{
            int choice = Integer.parseInt(scanner.nextLine());
            if( choice == lastOption )
                return;
            if( choice > 0 && choice <= clientBankAccounts.size() ){
                controller.setCurrentBankAccount(clientBankAccounts.get(choice - 1));
                bankAccountMenu();
            }else {
                System.err.println("Invalid number");
            }
        } catch (NumberFormatException e) {
            System.err.println("Please enter a valid number");
        }
    }

    private void chooseBankAccountType() {
        System.out.println("\n=-=-= Choose bank account type -0-0-0\n");
        System.out.println("1. Standard account");
        System.out.println("2. Savings account");
        System.out.println("3. Back to main menu");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> controller.createStandardAccount();
            case "2" -> controller.createSavingsAccount();
            case "3" -> {return;}
            default -> System.err.println("Incorrect number");
        }
    }

    private void bankAccountMenu() {
        AccountSummaryDTO account = controller.getCurrentAccountSummary();
        System.out.println("\n=-=-= Account -0-0-0\n");
        System.out.println("Account number: " + account.number());
        System.out.println("balance: " + account.balanceWithCurrency());
        System.out.println("\n=-=-= Menu -0-0-0\n");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. History");
        System.out.println("5. Exit");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> depositMenu();
            case "2" -> withdrawMenu();
            case "3" -> transferMenu();
            case "4" -> showHistory();
            case "5" -> {
                controller.setCurrentBankAccount(null);
                return;
            }
        }
    }

    private void showHistory() {
        List<SingleTransaction> history = controller.getCurrentBankAccount().getHistory();
        history.forEach(e -> System.out.println(e.toString()));
        System.out.println("Press anything to exit");
        scanner.nextLine();
    }

    private void depositMenu() {
        System.out.println("\n=-=-= Deposit -0-0-0\n");
        System.out.print("amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        controller.deposit(amount);
    }
    private void withdrawMenu() {
        System.out.println("\n=-=-= Withdraw -0-0-0\n");
        System.out.print("amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        controller.withdraw(amount);
    }
    private void transferMenu() {
        System.out.println("\n=-=-= Transfer -0-0-0\n");
        System.out.println("Transfer to(account number): ");
        String to = scanner.nextLine();
        System.out.print("amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        controller.transfer(to, amount);
    }
}
