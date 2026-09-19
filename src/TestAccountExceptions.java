public class TestAccountExceptions {

    public static void main(String[] args) {
        System.out.println("ACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("===========================");

        System.out.println("\n>>> Test 1: Valid Account Creation");
        Account acc1 = null;
        try {
            acc1 = new Account(1001, "John Doe", 25, 1000.0, "Savings");
            System.out.println("SUCCESS: Account #" + acc1.getAccountNumber() + " | " + acc1.getName() + " (" + acc1.getAge() + " yrs) | " + acc1.getAccountType() + " | " + acc1.getBalance() + " | " + acc1.getStatus() + " | PIN: " + (acc1.hasPin() ? "Yes" : "No"));
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n>>> Test 2: Invalid Age (under 18)");
        try {
            new Account(1002, "Young Kid", 16, 500.0, "Savings");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Customer must be at least 18 years old. Provided: 16");
        }

        System.out.println("\n>>> Test 3: Invalid Account Type");
        try {
            new Account(1003, "Test User", 25, 500.0, "Invalid");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Account type must be 'Savings' or 'Current'. Provided: Invalid");
        }

        System.out.println("\n>>> Test 4: Minimum Balance on Creation");
        try {
            new Account(1004, "Bob Wilson", 25, 300.0, "Savings");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Initial balance must be at least 500.0 for Savings account. Provided: 300.0");
        }

        System.out.println("\n>>> Test 5: Deposit Operations");
        Account acc5 = null;
        try {
            acc5 = new Account(1005, "Alice Brown", 30, 1000.0, "Current");
            acc5.deposit(300.0);
            System.out.println("SUCCESS: Deposited 300.0. New balance: " + acc5.getBalance());
            acc5.deposit(-50.0);
        } catch (InvalidAmountException | InactiveAccountException | IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n>>> Test 6: Withdrawal Operations");
        Account acc6 = null;
        try {
            acc6 = new Account(1006, "Charlie Green", 35, 1000.0, "Savings");
            acc6.setPin(1234);
            acc6.withdraw(500.0, 1234);
            System.out.println("SUCCESS: Withdrew 500.0. New balance: " + acc6.getBalance());
            acc6.withdraw(600.0, 1234);
        } catch (AccountException | IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n>>> Test 7: Account Status Management");
        Account acc7 = null;
        try {
            acc7 = new Account(1007, "Diana Prince", 28, 1000.0, "Savings");
            acc7.setPin(1234);
            acc7.closeAccount();
            System.out.println("SUCCESS: Closed account #" + acc7.getAccountNumber());
            acc7.deposit(200.0);
        } catch (AccountException | IllegalStateException | IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n>>> Test 8: PIN Protection");
        Account acc8 = null;
        try {
            acc8 = new Account(1008, "Eve Wilson", 32, 1000.0, "Current");
            System.out.println("Attempting to set invalid PIN (12)");
            acc8.setPin(12);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: PIN must be a 4-digit number");
        }

        System.out.println("\n>>> Test 9: Incorrect PIN Handling");
        try {
            if (acc6 != null) {
                System.out.println("Attempting to withdraw 100.0 with incorrect PIN (9999)");
                acc6.withdraw(100.0, 9999);
            }
        } catch (AccountException e) {
            System.out.println("EXCEPTION: Incorrect PIN");
        }

        System.out.println("\n>>> Test 10: PIN Not Set Handling");
        try {
            if (acc8 != null) {
                System.out.println("Attempting to withdraw 100.0 without PIN set");
                acc8.withdraw(100.0, 1234);
            }
        } catch (AccountException e) {
            System.out.println("EXCEPTION: PIN not set for this account");
        }

        System.out.println("\n>>> Test 11: All Accounts Summary");
        Account acc9 = null;
        try {
            acc9 = new Account(1009, "Frank Miller", 40, 3000.0, "Savings");
            acc9.setPin(1234);
            if (acc7 != null) acc7.reopenAccount();
        } catch (Exception ignored) {}

        if (acc1 != null) printSummary(acc1);
        if (acc5 != null) printSummary(acc5);
        if (acc6 != null) printSummary(acc6);
        if (acc7 != null) printSummary(acc7);
        if (acc8 != null) printSummary(acc8);
        if (acc9 != null) printSummary(acc9);

        System.out.println("\nTEST COMPLETED!");
    }

    private static void printSummary(Account acc) {
        System.out.println("Account #" + acc.getAccountNumber() + " | " + acc.getName() + " (" + acc.getAge() + " yrs) | " + acc.getAccountType() + " | " + acc.getBalance() + " | " + acc.getStatus() + " | PIN: " + (acc.hasPin() ? "Yes" : "No"));
    }
}