package com.github.azcx1.banksystem;

import com.github.azcx1.AppUtils.AccountSummaryDTO;
import com.github.azcx1.AppUtils.BankAccountServices;
import com.github.azcx1.AppUtils.ClientService;
import com.github.azcx1.BankConsoleApp;
import com.github.azcx1.banksystem.account.BankAccount;
import com.github.azcx1.banksystem.client.Client;
import com.github.azcx1.banksystem.common.model.account.AccountNumber;

import java.math.BigDecimal;
import java.util.*;

public class BankController {
    private final Scanner scanner;
    private final BankService bankService = new BankService();
    private final BankAccountServices bankAccountServices;

    private Client currentClient = null;
    private BankAccount currentBankAccount = null;
    private final List<Client> clients = new ArrayList<>();

    public BankController(Scanner scanner) {
        this.scanner = scanner;
        this.bankAccountServices = new BankAccountServices(this.scanner);
    }

    public Client getCurrentClient() {
        return this.currentClient;
    }
    public String getCurrentClientName() {
        return this.currentClient.getDisplayName();
    }
    public void setCurrentClient(Client client){
        currentClient = client;
    }

    public void setCurrentBankAccount(BankAccount account) {
        currentBankAccount = account;
    }
    public BankAccount getCurrentBankAccount() {
        return this.currentBankAccount;
    }
    public AccountSummaryDTO getCurrentAccountSummary() {
        if ( currentBankAccount == null)
            return null;
        return new AccountSummaryDTO(
                currentBankAccount.getAccountNumber().toString(),
                currentBankAccount.getAccountBalance() + " " + currentBankAccount.getAccountCurrency()
                );
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }
    public void addClient(Client client){
        clients.add(client);
    }

    public List<BankAccount> getClientBankAccountsToList() {
        if ( currentClient.getBankAccounts() == null)
            return Collections.emptyList();

        Map<AccountNumber, BankAccount> clientBankAccountsMap = currentClient.getBankAccounts();
        if ( clientBankAccountsMap.isEmpty())
            return Collections.emptyList();

        return new ArrayList<>(clientBankAccountsMap.values());
    }
    public void createStandardAccount() {
        Currency currency = bankAccountServices.chooseAccountCurrency();
        bankService.openStandardAccount(currentClient, currency);
    }
    public void createSavingsAccount() {
        Currency currency = bankAccountServices.chooseAccountCurrency();
        bankService.openSavingsAccount(currentClient, currency);
    }

    public void deposit(double amount) {
        bankService.deposit(
                currentBankAccount.getAccountNumber(),
                BigDecimal.valueOf(amount)
        );
    }
    public void withdraw(double amount) {
        bankService.withdraw(
                currentBankAccount.getAccountNumber(),
                BigDecimal.valueOf(amount)
        );
    }
    public void transfer(String to, double amount) {
        bankService.transfer(
                currentBankAccount.getAccountNumber(),
                new AccountNumber(to),
                BigDecimal.valueOf(amount)
        );
    }
}
