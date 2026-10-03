package com.gdb.tests;

import com.gdb.command.*;
import com.gdb.db.SimulatedDatabase;
import com.gdb.domain.*;
import com.gdb.logging.*;
import java.util.List;

public class TestBridgeLogging {
    private static final int EXPECTED_COUNT = 3;

    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 18 — BRIDGE PATTERN (FILE + DB)");
        System.out.println("=".repeat(60));

        // Setup test accounts
        IAccount acc1 = AccountFactory.createAccount("SAVINGS", 1001, "John", 25, 15000);
        acc1.setPin(1234);
        IAccount acc2 = AccountFactory.createAccount("SAVINGS", 1002, "Jane", 30, 10000);

        // ============================================================
        // 📝 STEP 24: Create Destinations
        //
        // INSTRUCTIONS:
        //   1. fileDest = new FileLogDestination(); fileDest.clear();
        //   2. db = new SimulatedDatabase();
        //   3. dbDest = new DatabaseLogDestination(db);
        //   4. memDest = new MemoryLogDestination();
        // ============================================================
        LogDestination fileDest = new FileLogDestination();
        fileDest.clear();
        SimulatedDatabase db = new SimulatedDatabase();
        LogDestination dbDest = new DatabaseLogDestination(db);
        LogDestination memDest = new MemoryLogDestination();

        // ============================================================
        // 📝 STEP 25: Create TransactionLogger with File Destination
        //
        // INSTRUCTIONS:
        //   1. logger = new TransactionLogger(fileDest);
        //   2. Execute and log 3 commands (Deposit, Withdraw, Transfer).
        //   3. Print count from logger.readAll().
        // ============================================================
        TransactionLogger logger = new TransactionLogger(fileDest);
        System.out.println();
        System.out.println("[STEP 25] Logging to " + logger.getDestinationName() + " destination...");
        executeAndLog(logger, acc1, acc2);
        System.out.println("  " + logger.getDestinationName() + " log count: " + logger.readAll().size());

        // ============================================================
        // 📝 STEP 26: Switch to Database Destination
        //
        // INSTRUCTIONS:
        //   1. logger.setDestination(dbDest);
        //   2. Execute and log 3 commands.
        //   3. Print count from logger.readAll().
        // ============================================================
        logger.setDestination(dbDest);
        System.out.println();
        System.out.println("[STEP 26] Switched to " + logger.getDestinationName() + " destination...");
        executeAndLog(logger, acc1, acc2);
        System.out.println("  " + logger.getDestinationName() + " log count: " + logger.readAll().size());

        // ============================================================
        // 📝 STEP 27: Switch to Memory Destination
        //
        // INSTRUCTIONS:
        //   1. logger.setDestination(memDest);
        //   2. Execute and log 3 commands.
        //   3. Print count from logger.readAll().
        // ============================================================
        logger.setDestination(memDest);
        System.out.println();
        System.out.println("[STEP 27] Switched to " + logger.getDestinationName() + " destination...");
        executeAndLog(logger, acc1, acc2);
        System.out.println("  " + logger.getDestinationName() + " log count: " + logger.readAll().size());

        // ============================================================
        // 📝 STEP 28: Verify Data Isolation Across Backends
        //
        // INSTRUCTIONS:
        //   1. logger.setDestination(fileDest); print count (should be 3).
        //   2. logger.setDestination(dbDest); print count (should be 3).
        //   3. logger.setDestination(memDest); print count (should be 3).
        // ============================================================
        System.out.println();
        System.out.println("[STEP 28] Verifying Data Isolation:");
        boolean allPassed = true;
        for (LogDestination dest : List.of(fileDest, dbDest, memDest)) {
            logger.setDestination(dest);
            int count = logger.readAll().size();
            allPassed &= count == EXPECTED_COUNT;
            System.out.println("  " + logger.getDestinationName() + " count: " + count
                    + " [EXPECTED: " + EXPECTED_COUNT + "]");
        }

        // ============================================================
        // 📝 STEP 29: Print Destination Names
        //
        // INSTRUCTIONS:
        //   Print getDestinationName() for each backend.
        // ============================================================
        System.out.println();
        if (allPassed) {
            System.out.println("[STEP 29] All Bridge Pattern log backends verified successfully!");
        } else {
            System.out.println("[STEP 29] Bridge Pattern verification FAILED for "
                    + fileDest.getDestinationName() + " / " + dbDest.getDestinationName()
                    + " / " + memDest.getDestinationName());
        }
    }

    // Executes one Deposit, Withdraw and Transfer command and logs each via the current destination
    private static void executeAndLog(TransactionLogger logger, IAccount from, IAccount to) throws Exception {
        TransactionCommand[] commands = {
            new DepositCommand(from, 5000),
            new WithdrawCommand(from, 2000, 1234),
            new TransferCommand(from, to, 3000, 1234)
        };
        for (TransactionCommand cmd : commands) {
            cmd.execute();
            logger.log(cmd);
        }
    }
}
