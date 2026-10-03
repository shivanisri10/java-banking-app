package com.gdb.ui;

import com.gdb.domain.IAccount;
import com.gdb.domain.Transaction;
import com.gdb.command.TransactionCommand;
import com.gdb.service.AccountService;
import com.gdb.exceptions.InvalidPinException;

import java.util.*;

public class AccountUI {
    // ============================================================
    // 📝 STEP 1: Declare Fields
    // ============================================================
    private final AccountService service;
    private final Scanner scanner;

    // ============================================================
    // 📝 STEP 2: Constructor
    //
    // INSTRUCTIONS:
    //   1. Accept AccountService parameter.
    //   2. Assign to this.service.
    //   3. Initialize scanner = new Scanner(System.in).
    // ============================================================
    public AccountUI(AccountService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // ============================================================
    // 📝 STEP 3: Implement start() (Main Menu Loop)
    //
    // INSTRUCTIONS:
    //   while (true) {
    //     displayMainMenu();
    //     int choice = readInt("Enter your choice: ");
    //     try {
    //       switch (choice) {
    //         case 1: handleOpenAccount(); break;
    //         case 2: handleDeposit(); break;
    //         case 3: handleWithdraw(); break;
    //         case 4: handleTransfer(); break;
    //         case 5: handleCloseAccount(); break;
    //         case 6: handleViewAccount(); break;
    //         case 7: handleViewTransactions(); break;
    //         case 8: System.out.println("Thank you! Goodbye."); return;
    //         default: System.out.println("Invalid choice. Please enter 1-8.");
    //       }
    //     } catch (Exception e) {
    //       System.out.println("ERROR: " + e.getMessage());
    //     }
    //   }
    // ============================================================
    public void start() {
        try {
            while (true) {
                displayMainMenu();
                int choice = readInt("Enter your choice: ");
                try {
                    switch (choice) {
                        case 1: handleOpenAccount(); break;
                        case 2: handleDeposit(); break;
                        case 3: handleWithdraw(); break;
                        case 4: handleTransfer(); break;
                        case 5: handleCloseAccount(); break;
                        case 6: handleViewAccount(); break;
                        case 7: handleViewTransactions(); break;
                        case 8: System.out.println("Thank you! Goodbye."); return;
                        default: System.out.println("Invalid choice. Please enter 1-8.");
                    }
                } catch (NoSuchElementException e) {
                    throw e;
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            // stdin was closed (e.g. Ctrl+D / Ctrl+Z or piped input ran out)
            System.out.println();
            System.out.println("Input closed. Goodbye.");
        }
    }

    // ============================================================
    // 📝 STEP 4: Implement displayMainMenu()
    // ============================================================
    private void displayMainMenu() {
        System.out.println();
        System.out.println("=".repeat(40));
        System.out.println("   GLOBAL DIGITAL BANK");
        System.out.println("=".repeat(40));
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Close Account");
        System.out.println("6. View Account Details");
        System.out.println("7. View Transaction History");
        System.out.println("8. Exit");
        System.out.println("=".repeat(40));
    }

    // ============================================================
    // 📝 STEP 5: Implement handleOpenAccount()
    //
    // INSTRUCTIONS:
    //   1. String type = readString("Account Type (Savings/Current/FixedDeposit/Salary): ")
    //   2. String name = readString("Name: ")
    //   3. int age = readInt("Age: ")
    //   4. double amount = readDouble("Initial Balance: ")
    //   5. IAccount acc = service.openAccount(type, name, age, amount)
    //   6. System.out.println("SUCCESS: " + acc.getAccountInfo())
    //   7. int pin = readInt("Set 4-digit PIN: ")
    //   8. acc.setPin(pin)
    // ============================================================
    private void handleOpenAccount() throws Exception {
        System.out.println();
        System.out.println("--- Open Account ---");
        // AccountFactory only supports these three types (no SalaryAccount class exists)
        String type = readString("Account Type (Savings/Current/FixedDeposit): ");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        double amount = readDouble("Initial Balance: ");

        IAccount acc = service.openAccount(type, name, age, amount);
        System.out.println("SUCCESS: " + acc.getAccountInfo());

        // The account already exists at this point, so keep asking until a valid PIN is set
        while (true) {
            int pin = readInt("Set 4-digit PIN: ");
            try {
                acc.setPin(pin);
                System.out.println("PIN set successfully.");
                return;
            } catch (InvalidPinException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // ============================================================
    // 📝 STEP 6: Implement handleDeposit()
    //
    // INSTRUCTIONS:
    //   1. int accNo = readInt("Account Number: ")
    //   2. double amount = readDouble("Amount to deposit: ")
    //   3. Transaction txn = service.deposit(accNo, amount)
    //   4. Print success + new balance
    // ============================================================
    private void handleDeposit() throws Exception {
        System.out.println();
        System.out.println("--- Deposit ---");
        int accNo = readInt("Account Number: ");
        double amount = readDouble("Amount to deposit: ");
        Transaction txn = service.deposit(accNo, amount);
        System.out.println("SUCCESS: Deposited Rs. " + amount + " to #" + accNo
                + ". New balance: Rs. " + txn.getBalanceAfter());
    }

    // ============================================================
    // 📝 STEP 7: Implement handleWithdraw()
    //
    // INSTRUCTIONS:
    //   1. int accNo = readInt("Account Number: ")
    //   2. double amount = readDouble("Amount to withdraw: ")
    //   3. int pin = readInt("PIN: ")
    //   4. Transaction txn = service.withdraw(accNo, amount, pin)
    //   5. Print success + new balance
    // ============================================================
    private void handleWithdraw() throws Exception {
        System.out.println();
        System.out.println("--- Withdraw ---");
        int accNo = readInt("Account Number: ");
        double amount = readDouble("Amount to withdraw: ");
        int pin = readInt("PIN: ");
        Transaction txn = service.withdraw(accNo, amount, pin);
        System.out.println("SUCCESS: Withdrew Rs. " + amount + " from #" + accNo
                + ". New balance: Rs. " + txn.getBalanceAfter());
    }

    // ============================================================
    // 📝 STEP 8: Implement handleTransfer()
    //
    // INSTRUCTIONS:
    //   1. int fromAcc = readInt("From Account: ")
    //   2. int toAcc = readInt("To Account: ")
    //   3. double amount = readDouble("Amount: ")
    //   4. int pin = readInt("PIN: ")
    //   5. Transaction txn = service.transfer(fromAcc, toAcc, amount, pin)
    //   6. Print success message
    // ============================================================
    private void handleTransfer() throws Exception {
        System.out.println();
        System.out.println("--- Transfer ---");
        int fromAcc = readInt("From Account: ");
        int toAcc = readInt("To Account: ");
        double amount = readDouble("Amount: ");
        int pin = readInt("PIN: ");
        Transaction txn = service.transfer(fromAcc, toAcc, amount, pin);
        System.out.println("SUCCESS: Transferred Rs. " + amount + " from #" + fromAcc + " to #" + toAcc
                + ". New balance of #" + fromAcc + ": Rs. " + txn.getBalanceAfter());
    }

    // ============================================================
    // 📝 STEP 9: Implement handleCloseAccount()
    //
    // INSTRUCTIONS:
    //   1. int accNo = readInt("Account Number: ")
    //   2. int pin = readInt("PIN: ")
    //   3. service.closeAccount(accNo, pin)
    //   4. Print success message
    // ============================================================
    private void handleCloseAccount() throws Exception {
        System.out.println();
        System.out.println("--- Close Account ---");
        int accNo = readInt("Account Number: ");
        int pin = readInt("PIN: ");
        service.closeAccount(accNo, pin);
        System.out.println("SUCCESS: Account #" + accNo + " has been closed.");
    }

    // ============================================================
    // 📝 STEP 10: Implement handleViewAccount()
    //
    // INSTRUCTIONS:
    //   1. int accNo = readInt("Account Number: ")
    //   2. IAccount acc = service.getAccount(accNo)
    //   3. If null -> "Account not found: " + accNo
    //   4. Else -> print acc.getAccountInfo()
    // ============================================================
    private void handleViewAccount() {
        System.out.println();
        System.out.println("--- Account Details ---");
        int accNo = readInt("Account Number: ");
        IAccount acc = service.getAccount(accNo);
        if (acc == null) {
            System.out.println("Account not found: " + accNo);
        } else {
            System.out.println(acc.getAccountInfo());
        }
    }

    // ============================================================
    // 📝 STEP 11: Implement handleViewTransactions()
    //
    // INSTRUCTIONS:
    //   1. List<TransactionCommand> history = service.getTransactionHistory()
    //   2. If empty -> "No transactions logged."
    //   3. Else print each with index
    // ============================================================
    private void handleViewTransactions() {
        System.out.println();
        System.out.println("--- Transaction History ---");
        List<TransactionCommand> history = service.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions logged.");
            return;
        }
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + history.get(i).getTransaction());
        }
    }

    // ============================================================
    // 📝 STEP 12: Implement readInt(String prompt)
    // ============================================================
    private int readInt(String prompt) {
        while (true) {
            String input = readString(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }

    // ============================================================
    // 📝 STEP 13: Implement readDouble(String prompt)
    // ============================================================
    private double readDouble(String prompt) {
        while (true) {
            String input = readString(prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a numeric value.");
            }
        }
    }

    // ============================================================
    // 📝 STEP 14: Implement readString(String prompt)
    // ============================================================
    private String readString(String prompt) {
        System.out.print(prompt);
        // nextLine() throws NoSuchElementException once input is exhausted; start() handles it
        return scanner.nextLine().trim();
    }
}
