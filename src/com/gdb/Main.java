package com.gdb;

import com.gdb.command.TransactionCommand;
import com.gdb.domain.IAccount;
import com.gdb.logging.FileLogDestination;
import com.gdb.logging.LogDestination;
import com.gdb.logging.TransactionLogger;
import com.gdb.service.AccountService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Global Digital Bank — Service Demo");
        System.out.println("=".repeat(60));

        // ============================================================
        // 📝 STEP 20: Wire Up Dependencies
        //
        // INSTRUCTIONS:
        //   1. LogDestination dest = new FileLogDestination();
        //   2. TransactionLogger logger = new TransactionLogger(dest);
        //   3. AccountService service = new AccountService(logger);
        // ============================================================
        LogDestination dest = new FileLogDestination();
        dest.clear(); // accounts are in-memory, so start each demo run with an empty log
        TransactionLogger logger = new TransactionLogger(dest);
        AccountService service = new AccountService(logger);

        // ============================================================
        // 📝 STEP 21: Run A Demo Workflow
        //
        // INSTRUCTIONS:
        //   1. Open accounts, set PINs, perform deposit, withdraw, transfer.
        //   2. Print balances and transaction history.
        // ============================================================
        IAccount alice = service.openAccount("SAVINGS", "Alice Kumar", 32, 40000);
        alice.setPin(1111);
        IAccount bob = service.openAccount("CURRENT", "Bob Menon", 45, 25000);
        bob.setPin(2222);

        System.out.println();
        System.out.println("Opened accounts:");
        for (IAccount account : service.getAllAccounts()) {
            System.out.println("  " + account.getAccountInfo());
        }

        System.out.println();
        System.out.println("Transactions:");
        System.out.println("  " + service.deposit(alice.getAccountNumber(), 10000));
        System.out.println("  " + service.withdraw(bob.getAccountNumber(), 5000, 2222));
        System.out.println("  " + service.transfer(alice.getAccountNumber(), bob.getAccountNumber(), 7500, 1111));

        System.out.println();
        System.out.println("Final balances:");
        for (IAccount account : service.getAllAccounts()) {
            System.out.println("  " + account.getAccountHolderName() + " (Account #"
                    + account.getAccountNumber() + "): Rs. " + account.getBalance());
        }

        System.out.println();
        System.out.println("Transaction history (" + logger.getDestinationName() + " log):");
        for (TransactionCommand cmd : service.getTransactionHistory()) {
            System.out.println("  " + cmd.getTransaction());
        }
    }
}
