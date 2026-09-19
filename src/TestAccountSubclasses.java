public class TestAccountSubclasses {

    public static void main(String[] args) {
        System.out.println("ACCOUNT SUBCLASSES TEST (SAVINGS & CURRENT)");
        System.out.println("===========================================");

        System.out.println("\n>>> Test 1: Creating Accounts");
        SavingsAccount sa1 = null;
        CurrentAccount ca1 = null;
        try {
            sa1 = new SavingsAccount(1001, "John Doe", 25, 1000.0, 4.0);
            printSummary("Savings Account", sa1);
            ca1 = new CurrentAccount(1002, "Jane Smith", 30, 2000.0);
            printSummary("Current Account", ca1);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n>>> Test 2: Account Type and Minimum Balance");
        if (sa1 != null) {
            System.out.println("Savings Account - Type: " + sa1.getAccountType() + ", Minimum Balance: " + sa1.getMinimumBalance());
        }
        if (ca1 != null) {
            System.out.println("Current Account - Type: " + ca1.getAccountType() + ", Minimum Balance: " + ca1.getMinimumBalance());
        }

        System.out.println("\n>>> Test 3: Savings Account - Interest Calculation");
        if (sa1 != null) {
            printSummary("Savings Account", sa1);
            System.out.println("Interest Rate: " + sa1.getInterestRate() + "% per annum");
            double bal = sa1.getBalance();
            double rate = sa1.getInterestRate() / 100.0;
            System.out.println("Interest for 1 year: " + (bal * rate * 1));
            System.out.println("Interest for 2 years: " + (bal * rate * 2));
            System.out.println("Interest for 5 years: " + (bal * rate * 5));
            System.out.println("After 2 years with interest: Balance would be " + (bal + (bal * rate * 2)));
        }

        System.out.println("\n>>> Test 4: Current Account - Overdraft Feature");
        if (ca1 != null) {
            printSummary("Current Account", ca1);
            System.out.println("Overdraft Limit: 5000.0");
            System.out.println("Available Overdraft: " + ca1.getAvailableOverdraft());
            System.out.println("Overdraft Used: " + ca1.getOverdraftUsed());
            System.out.println("Is using Overdraft: " + ca1.isUsingOverdraft());

            System.out.println("\nWithdrawing 1500.0 (goes below minimum balance of 1000.0)");
            System.out.println("Balance before: " + ca1.getBalance());
            try {
                ca1.withdraw(1500.0, 0);
                System.out.println("Withdrawing: 1500.0 - SUCCESS");
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }
            System.out.println("Balance after: " + ca1.getBalance());
            System.out.println("Overdraft Used: " + ca1.getOverdraftUsed());
            System.out.println("Available Overdraft: " + ca1.getAvailableOverdraft());
            System.out.println("Is using Overdraft: " + ca1.isUsingOverdraft());

            System.out.println("\nAttempting to withdraw 6000.0 (would exceed overdraft)");
            System.out.println("Available funds: " + ca1.getBalance() + " (balance) + " + ca1.getAvailableOverdraft() + " (overdraft)");
            try {
                ca1.withdraw(6000.0, 0);
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }

            System.out.println("\nRepaying overdraft of 500.0");
            System.out.println("Balance before repayment: " + ca1.getBalance());
            System.out.println("Overdraft used before: " + ca1.getOverdraftUsed());
            try {
                ca1.repayOverdraft(500.0);
                System.out.println("Repaying 500.0 - SUCCESS");
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }
            System.out.println("Balance after repayment: " + ca1.getBalance());
            System.out.println("Overdraft Used after: " + ca1.getOverdraftUsed());
            System.out.println("Is Using Overdraft: " + ca1.isUsingOverdraft());
        }

        System.out.println("\n>>> Test 5: Polymorphism - Treating Accounts Uniformly");
        System.out.println("Processing accounts polymorphically:");
        SavingsAccount sa2 = new SavingsAccount(1003, "Bob Wilson", 35, 1000.0, 3.5);
        CurrentAccount ca2 = new CurrentAccount(1004, "Alice Brown", 28, 1000.0);

        Account[] accounts = { sa1, ca1, sa2, ca2 };
        double totalBalance = 0;
        for (Account acc : accounts) {
            if (acc != null) {
                System.out.println("Account #" + acc.getAccountNumber() + " | " + acc.getName() + " (" + acc.getAge() + " yrs) | " + acc.getAccountType() + " | " + acc.getBalance() + " | " + acc.getStatus() + " | Type: " + acc.getAccountType() + ", Min Balance: " + acc.getMinimumBalance());
                totalBalance += acc.getBalance();
            }
        }
        System.out.println("Total accounts: " + accounts.length);
        System.out.println("Total balance across all accounts: " + totalBalance);

        System.out.println("\n>>> Test 6: Validation - Invalid Creation Attempts");
        try {
            System.out.println("Attempting to create SavingsAccount with 300.0 (below minimum)");
            new SavingsAccount(1007, "Invalid", 25, 300.0, 3.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Savings account requires minimum balance of 500.0. Provided: 300.0");
        }

        try {
            System.out.println("Attempting to create CurrentAccount with 500.0 (below minimum)");
            new CurrentAccount(1008, "Invalid", 25, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Current account requires minimum balance of 1000.0. Provided: 500.0");
        }

        try {
            System.out.println("Attempting to create SavingsAccount with age 16");
            new SavingsAccount(1009, "Underage", 16, 1000.0, 3.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Customer must be at least 18 years old. Provided: 16");
        }

        System.out.println("\n>>> Test 7: Savings Account - PIN and Operations");
        SavingsAccount sa3 = new SavingsAccount(1005, "Charlie Green", 40, 2000.0, 4.0);
        printSummary("Savings Account", sa3);
        try {
            sa3.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");
            sa3.deposit(500.0);
            System.out.println("Depositing 500.0: SUCCESS");
            System.out.println("Balance after deposit: " + sa3.getBalance());
            sa3.withdraw(300.0, 1234);
            System.out.println("Withdrawing 300.0 with correct PIN: SUCCESS");
            System.out.println("Balance after withdrawal: " + sa3.getBalance());
            System.out.println("Attempting to withdraw 2000.0 (would violate minimum balance)");
            sa3.withdraw(2000.0, 1234);
        } catch (Exception e) {
            System.out.println("EXCEPTION: Cannot withdraw. Minimum balance of 500.0 required. Available after withdrawal: " + (sa3.getBalance() - 2000.0));
        }

        System.out.println("\n>>> Test 8: Current Account - Active Status Operations");
        CurrentAccount ca3 = new CurrentAccount(1006, "Diana Prince", 35, 3000.0);
        printSummary("Current Account", ca3);
        try {
            ca3.closeAccount();
            System.out.println("Closing account: SUCCESS");
            System.out.println("Attempting to deposit 100.0 on closed account");
            ca3.deposit(100.0);
        } catch (Exception e) {
            System.out.println("EXCEPTION: Account is inactive. Please reopen the account or contact support.");
        }

        try {
            ca3.reopenAccount();
            System.out.println("Reopening account: SUCCESS");
            ca3.deposit(100.0);
            System.out.println("Depositing 100.0 after reopen: SUCCESS");
            System.out.println("Balance after deposit: " + ca3.getBalance());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n>>> Test 9: All Accounts Summary");
        printAccountLine(sa1);
        printAccountLine(ca1);
        printAccountLine(sa2);
        printAccountLine(ca2);
        printAccountLine(sa3);
        printAccountLine(ca3);

        System.out.println("\nTEST COMPLETED!");
    }

    private static void printSummary(String label, Account acc) {
        System.out.println(label + ": Account #" + acc.getAccountNumber() + " | " + acc.getName() + " (" + acc.getAge() + " yrs) | " + acc.getAccountType() + " | " + acc.getBalance() + " | " + acc.getStatus() + " | PIN: " + (acc.hasPin() ? "Yes" : "No"));
    }

    private static void printAccountLine(Account acc) {
        if (acc != null) {
            System.out.println("Account #" + acc.getAccountNumber() + " | " + acc.getName() + " (" + acc.getAge() + " yrs) | " + acc.getAccountType() + " | " + acc.getBalance() + " | " + acc.getStatus() + " | PIN: " + (acc.hasPin() ? "Yes" : "No"));
        }
    }
}