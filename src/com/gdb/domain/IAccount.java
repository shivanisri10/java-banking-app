package com.gdb.domain;

import com.gdb.exceptions.*;

public interface IAccount {

    void deposit(double amount) throws InactiveAccountException, InvalidAmountException;

    void withdraw(double amount, int pin) throws InactiveAccountException, InvalidPinException, InvalidAmountException, InsufficientBalanceException;

    double getBalance();

    int getAccountNumber();

    String getAccountHolderName();

    String getAccountType();

    String getOpeningDate();

    boolean isActive();

    double getMinimumBalance();

    double getInterestRate();

    boolean canWithdraw(double amount);

    void setPin(int pin) throws InvalidPinException;

    boolean verifyPin(int pin);

    boolean hasPin();

    void closeAccount() throws InactiveAccountException;

    void reopenAccount() throws InactiveAccountException;

    String getAccountInfo();

    int getTenureYears();

    void setTenureYears(int tenureYears);
}
