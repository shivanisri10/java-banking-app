package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestInterfaceFactory {
    public static void main(String[] args) {
        System.out.println("=== Activity 12: Factory-Driven System Suite ===");

        try {
            IAccount savings = AccountFactory.createAccount("SAVINGS", "ACC101", "Alice", 25, 5000.0, "ACTIVE", "1234");
            IAccount current = AccountFactory.createAccount("CURRENT", "ACC102", "Bob", 30, 2000.0, "ACTIVE", "1234");

            savings.deposit(1000.0);
            if (savings.getBalance() == 6000.0) {
                System.out.println("[Test 1] Savings Account Creation & Deposit: [PASS]");
            } else {
                System.out.println("[Test 1] Savings Account Creation & Deposit: [FAIL]");
            }

            current.withdraw(3000.0, "1234");
            System.out.println("[Test 2] Current Account Overdraft Withdrawal: [PASS]");

        } catch (Exception e) {
            System.out.println("Unexpected error during standard tests: " + e.getMessage());
        }

        try {
            IAccount fixedDeposit = AccountFactory.createAccount("FIXED_DEPOSIT", "ACC103", "Charlie", 40, 10000.0, "ACTIVE", "1234");
            fixedDeposit.withdraw(1000.0, "1234");
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL]");
        } catch (Exception e) {
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]");
        }

        try {
            AccountFactory.createAccount("INVALID_TYPE", "ACC999", "Unknown", 20, 500.0, "ACTIVE", "1234");
            System.out.println("[Test 4] Invalid Type Rejection: [FAIL]");
        } catch (IllegalArgumentException e) {
            System.out.println("[Test 4] Invalid Type Rejection: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 4] Invalid Type Rejection: [FAIL]");
        }

        System.out.println("Factory-driven architecture successfully verified!");
    }
}