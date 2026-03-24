package com.github.azcx1.banksystem.account;

import com.github.azcx1.banksystem.client.Client;
import com.github.azcx1.banksystem.common.model.account.interfaces.IntrestBearing;

import java.math.BigDecimal;
import java.util.Currency;

public class SavingsAccount extends BankAccount
        implements IntrestBearing {

    private final BigDecimal interestRate;

    public SavingsAccount(Client owner, Currency currency, BigDecimal interestRate){
        super(owner, currency);

        if(interestRate == null || interestRate.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Interest rate can not be lower than 0");
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest(){
        BigDecimal currentBalance = getAccountBalance();

        if ( currentBalance.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal interest = currentBalance.multiply(this.interestRate);
            super.deposit(interest);
        }
    }
    @Override
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(5.00)) < 0)
            throw new IllegalArgumentException(String.format( "Minimum value of deposid is 5.00%s", getAccountCurrency().getSymbol()));
        super.deposit(amount);
    }
    @Override
    public void withdraw(BigDecimal amount){
        BigDecimal withdrawFee = BigDecimal.valueOf(5.00);
        BigDecimal totalAmount = amount.add(withdrawFee);

        super.withdraw(totalAmount);
    }
}
