package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.service.TransferService;
import com.gdb.exceptions.*;

public class TestTransfer {
    private static final int PIN = 1234;

    private static TransferService svc;
    private static Account acc1;
    private static Account acc2;

    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 15 — TRANSFER WITH DAILY LIMITS");
        System.out.println("=".repeat(60));

        svc = new TransferService();
        AccountRulesEngine.getInstance();

        createAccountsAndSetPin();
        testSuccessfulTransfer();
        testInsufficientBalance();
        testDailyLimitBreach();
        testRemainingLimit();
    }

    // STEP 9: Create two Savings accounts and set the PIN on the sender
    private static void createAccountsAndSetPin() throws AccountException {
        acc1 = (Account) AccountFactory.createAccount("SAVINGS", 1001, "Rajesh Sharma", 30, 100000);
        acc2 = (Account) AccountFactory.createAccount("SAVINGS", 1002, "Priya Patel", 28, 20000);
        acc1.setPin(PIN);

        System.out.println();
        System.out.println("[STEP 9] " + acc1.getAccountInfo());
        System.out.println("[STEP 9] " + acc2.getAccountInfo());
    }

    // STEP 10: A transfer within balance and daily limit succeeds
    private static void testSuccessfulTransfer() throws AccountException {
        svc.transfer(acc1, acc2, 5000, PIN);

        System.out.println();
        System.out.println("[STEP 10] Transfer Rs. 5,000: SUCCESS | acc1 = Rs. " + acc1.getBalance()
                + " | acc2 = Rs. " + acc2.getBalance());
    }

    // STEP 11: A transfer larger than the withdrawable balance is rejected
    private static void testInsufficientBalance() throws AccountException {
        try {
            svc.transfer(acc1, acc2, 1_00_000, PIN);
            System.out.println("[STEP 11] ERROR: transfer should have failed");
        } catch (InsufficientBalanceException e) {
            System.out.println("[STEP 11] Caught InsufficientBalanceException: " + e.getMessage());
        }
    }

    // STEP 12: Repeated transfers stop once the daily limit is crossed
    private static void testDailyLimitBreach() {
        System.out.println("[STEP 12] Daily limit for acc1: Rs. " + acc1.getDailyTransferLimit());

        int attempt = 1;
        try {
            while (true) {
                svc.transfer(acc1, acc2, 20000, PIN);
                System.out.println("  Transfer #" + attempt + " of Rs. 20,000: SUCCESS | used today = Rs. "
                        + acc1.getDailyTransferTotal());
                attempt++;
            }
        } catch (AccountException e) {
            System.out.println("[STEP 12] Caught AccountException: " + e.getMessage());
        }
    }

    // STEP 13: Used + remaining must equal the daily limit
    private static void testRemainingLimit() {
        System.out.println("[STEP 13] Used today: Rs. " + acc1.getDailyTransferTotal()
                + " | Remaining: Rs. " + acc1.getRemainingDailyTransferLimit());
    }
}
