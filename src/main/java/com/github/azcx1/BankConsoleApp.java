package com.github.azcx1;

import com.github.azcx1.AppUtils.BankAccountServices;
import com.github.azcx1.AppUtils.ClientService;
import com.github.azcx1.AppUtils.Exceptions.EmptyBankAccountsListException;
import com.github.azcx1.AppUtils.Exceptions.EmptyClientListException;
import com.github.azcx1.banksystem.BankService;
import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.client.Client;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.util.*;

public class BankConsoleApp {
    public final Scanner scanner = new Scanner(System.in);
    private final BankService bankService = new BankService();
    private final ClientService clientService = new ClientService(scanner);
    private final BankAccountServices bankAccountServices = new BankAccountServices(scanner);

    private Client currentCLient = null;
    private BankAccount currentBankAccount = null;

    private final List<Client> clients = new ArrayList<>();

    public void start() {
        while (true) {
            try{
                if (currentCLient == null)
                    showMainMenu();
                if (currentBankAccount == null)
                    clientMenu();
                else
                    bankAccountMenu();
            } catch (EmptyClientListException e) {
                System.err.println("\n[!] " + e.getMessage());
            } catch (EmptyBankAccountsListException e){
                System.err.println("[!] " + e.getMessage());
            } catch (Exception e){
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

        switch (choice){
            case "1" -> handleClientSelection();
            case "2" -> chooseClientType();
            case "3" -> System.exit(0);
            default -> System.err.println("Incorrect number");
        }
    }

    private void handleClientSelection() {
        if ( clients == null || clients.isEmpty() )
            throw new EmptyClientListException("List is empty. Add a client first.");

        System.out.println("\n=-=-= Select client -0-0-0\n");
        for( int i = 0; i < clients.size(); i++ ) {
            System.out.println((i+1) + ". " + clients.get(i).getDisplayName());
        }
        int lastOption = clients.size() + 1;
        System.out.println(lastOption + ". Back to main menu");

        System.out.print("\nChoice: ");
        try{
            int choice = Integer.parseInt(scanner.nextLine());

            if ( choice == lastOption )
                return;
            if ( choice > 0 && choice <= clients.size() ) {
                this.currentCLient = clients.get(choice - 1);
                clientMenu();
            } else {
                System.err.println("Invalid number");
            }
        } catch (NumberFormatException e){
            System.err.println("Please enter a valid number");
        }
    }
    private void chooseClientType(){
        System.out.println("\n=-=-= Choose client type -0-0-0\n");
        System.out.println("1. Individual customer");
        System.out.println("2. Corporative customer");
        System.out.println("3. Back to main menu");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> createIndividualCustomer();
            case "2" -> createCorporateCustomer();
            case "3" -> {return;}
            default -> System.err.println("Incorrect number");
        }
    }

    private void createIndividualCustomer(){
        clients.add(
                clientService.createIndividualCustomer()
        );
    }
    private void createCorporateCustomer(){
        clients.add(
                clientService.createCorporateCustomer()
        );
    }

    private void clientMenu() {
        if (currentCLient == null )
            return;
        System.out.println("\n=-=-= Client menu -0-0-0\n");
        System.out.println("Hello, " + currentCLient.getDisplayName());
        System.out.println("1. Choose bank account");
        System.out.println("2. Create bank account");
        System.out.println("3. Logout");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> handleBankAccountSelection();
            case "2" -> chooseBankAccountType();
            case "3" -> {
                this.currentCLient = null;
                return;
            }
            default -> System.err.println("Incorrect number");
        }
    }

    private void handleBankAccountSelection(){
        Map<AccountNumber, BankAccount> clientBankAccountsMap = currentCLient.getBankAccounts();
        if (clientBankAccountsMap == null || clientBankAccountsMap.isEmpty())
            throw new EmptyBankAccountsListException("List is empty. Create bank account first.");

        System.out.println("\n=-=-= Select bank account -0-0-0\n");

        List<BankAccount> clientBankAccounts = new ArrayList<>(clientBankAccountsMap.values());

        for (int i = 0; i < clientBankAccounts.size(); i++) {
            BankAccount account = clientBankAccounts.get(i);
            System.out.println((i+1) + ". " + account.getAccountNumber()
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
                this.currentBankAccount = clientBankAccounts.get(choice-1);
                bankAccountMenu();
            }else {
                System.err.println("Invalid number");
            }
        } catch (NumberFormatException e){
            System.err.println("Please enter a valid number");
        }
    }
    private void chooseBankAccountType(){
        System.out.println("\n=-=-= Choose bank account type -0-0-0\n");
        System.out.println("1. Standard account");
        System.out.println("2. Savings account");
        System.out.println("3. Back to main menu");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> createStandardAccount();
            case "2" -> createSavingsAccount();
            case "3" -> {return;}
            default -> System.err.println("Incorrect number");
        }
    }
    private void createStandardAccount(){
        String currency = bankAccountServices.chooseAccountCurrency();
        bankService.openStandardAccount(this.currentCLient, currency);
    }
    private void createSavingsAccount(){
        String currency = bankAccountServices.chooseAccountCurrency();
        bankService.openSavingsAccount(this.currentCLient, currency);
    }

    private void bankAccountMenu(){
        System.out.println("\n=-=-= Account -0-0-0\n");
        System.out.println("Account number: " + this.currentBankAccount.getAccountNumber());
        System.out.println("balance: " + this.currentBankAccount.getAccountBalance() + " " + this.currentBankAccount.getAccountCurrency());
        System.out.println("\n=-=-= Menu -0-0-0\n");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Exit");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> depositMenu();
            case "2" -> withdrawMenu();
            case "3" -> transferMenu();
            case "4" -> {
                this.currentBankAccount = null;
                return;
            }
        }
    }

    private void depositMenu() {
        System.out.println("\n=-=-= Deposit -0-0-0\n");
        System.out.print("amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        currentBankAccount.deposit(amount);
    }
    private void withdrawMenu() {
        System.out.println("\n=-=-= Withdraw -0-0-0\n");
        System.out.print("amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        currentBankAccount.withdraw(amount);
    }
    private void transferMenu() {
        System.out.println("\n=-=-= Transfer -0-0-0\n");
        System.out.println("Transfer to(account number): ");
        AccountNumber transferTo = new AccountNumber(scanner.nextLine());
        System.out.print("amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        bankService.transfer(currentBankAccount.getAccountNumber(), transferTo, amount);
    }
}
