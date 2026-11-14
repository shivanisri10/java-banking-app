package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.repository.*;
import com.gdb.service.AccountService;
import com.gdb.logging.*;
import java.util.List;

public class TestRepositoryInMemory {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 21 — REPOSITORY PATTERN (IN-MEMORY)");
        System.out.println("=".repeat(60));

        AccountRepository accountRepo = new InMemoryAccountRepository();
        TransactionRepository txnRepo = new InMemoryTransactionRepository();
        TransactionLogger logger = new TransactionLogger(new MemoryLogDestination());

        AccountService service = new AccountService(accountRepo, txnRepo, logger);

        // ============================================================
        // 📝 STEP 17: Test Account Repository CRUD
        // ============================================================
        // STEP 17: Test Account Repository CRUD
        IAccount sample = AccountFactory.createAccount("SAVINGS", 9001, "Test User", 20, 10000);
        sample.setPin(1234);
        accountRepo.save(sample);
        System.out.println("Account saved: " + (accountRepo.findById(9001) != null));
        System.out.println("Account count: " + accountRepo.findAll().size());
        sample.deposit(500);
        accountRepo.update(sample);
        System.out.println("Updated balance: " + accountRepo.findById(9001).getBalance());
        System.out.println("Account exists: " + accountRepo.exists(9001));
        System.out.println("Next account number: " + accountRepo.nextAccountNumber());

        // STEP 18: Test Transaction Repository Operations
        Transaction sampleTxn = new Transaction(
                Transaction.generateId(), java.time.LocalDateTime.now(), 9001,
                TransactionType.DEPOSIT, 500, sample.getBalance(), "SUCCESS",
                "Repository test", 0, 0);
        txnRepo.save(sampleTxn);
        System.out.println("Transactions saved: " + txnRepo.findAll().size());
        System.out.println("Transactions for 9001: " + txnRepo.findByAccount(9001).size());
        txnRepo.clear();
        System.out.println("Transactions after clear: " + txnRepo.findAll().size());

        // STEP 19: Test Service Integration With Repositories
        IAccount first = service.openAccount("SAVINGS", "Shivani", 20, 20000);
        IAccount second = service.openAccount("SAVINGS", "Student Two", 21, 20000);
        first.setPin(1234);
        second.setPin(2345);
        int firstNo = first.getAccountNumber();
        int secondNo = second.getAccountNumber();
        service.deposit(firstNo, 3000);
        service.withdraw(firstNo, 1000, 1234);
        service.transfer(firstNo, secondNo, 2000, 1234);
        System.out.println("Service accounts opened: " + service.getAllAccounts().size());
        System.out.println("First account balance: " + service.getAccount(firstNo).getBalance());
        System.out.println("Second account balance: " + service.getAccount(secondNo).getBalance());
        System.out.println("Service transaction records: " + service.getTransactionRecords().size());

        System.out.println("Activity 21 Repository tests complete.");
    }
}
