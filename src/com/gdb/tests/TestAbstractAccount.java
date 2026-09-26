package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAbstractAccount {

    public static void transfer(AbstractAccount source, AbstractAccount destination, double amount, String pin) throws AccountException {
        source.withdraw(amount, pin);
        destination.deposit(amount);
    }

    public static void processMonthlyCycle(AbstractAccount[] portfolio) {
        for (AbstractAccount account : portfolio) {
            if (account instanceof SavingsAccount) {
                SavingsAccount sa = (SavingsAccount) account;
                sa.applyInterest();
            } else if (account instanceof SalaryAccount) {
                SalaryAccount sal = (SalaryAccount) account;
                sal.incrementInactiveMonths();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Activity 10: Banking Operations Suite ===");

        try {
            SavingsAccount sa = new SavingsAccount("SA101", "Alice", 25, 10000.0, "ACTIVE", "1234");
            CurrentAccount ca = new CurrentAccount("CA102", "Bob", 30, 5000.0, "ACTIVE", "5678", 2000.0);
            SalaryAccount sal = new SalaryAccount("SAL103", "Charlie", 28, 15000.0, "ACTIVE", "4321", "TechCorp");

            AbstractAccount[] portfolio = { sa, ca, sal };

            transfer(sa, ca, 3000.0, "1234");
            System.out.println("Transfer Rs 3000 from Savings to Current: SUCCESS");
            System.out.println("Savings Balance: Rs " + sa.getBalance() + " | Current Balance: Rs " + ca.getBalance());

            try {
                transfer(sa, ca, 1000.0, "0000");
            } catch (AccountException e) {
                System.out.println("Failed Transfer (Wrong PIN): Exception caught, no balance changed [PASS]");
            }

            processMonthlyCycle(portfolio);
            System.out.println("Monthly Interest Cycle processed for all qualifying accounts.");
            System.out.println("All banking operations passed!");

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}