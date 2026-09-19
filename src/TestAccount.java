public class TestAccount {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("GLOBAL DIGITAL BANK - ACCOUNT TEST");
        System.out.println("==================================================");

        System.out.println(">>> 1. Creating Account");
        Account acc1 = new Account(1001, "John Doe", 25, 1000.0, "Savings");
        System.out.println("Account created!");
        displayAccount(acc1);

        System.out.println("\n>>> 2. Deposit Money");
        double depositValid = 500.0;
        if (acc1.deposit(depositValid)) {
            System.out.println("Depositing ₹" + depositValid + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } else {
            System.out.println("Depositing ₹" + depositValid + ": FAILED (Invalid amount)");
        }

        double depositInvalid = -100.0;
        if (acc1.deposit(depositInvalid)) {
            System.out.println("Depositing ₹" + depositInvalid + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } else {
            System.out.println("Depositing ₹" + depositInvalid + ": FAILED (Invalid amount)");
        }

        System.out.println("\n>>> 3. Withdraw Money");
        double withdrawValid = 200.0;
        if (acc1.withdraw(withdrawValid)) {
            System.out.println("Withdrawing ₹" + withdrawValid + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } else {
            System.out.println("Withdrawing ₹" + withdrawValid + ": FAILED (Insufficient balance)");
        }

        double withdrawInvalid = 2000.0;
        if (acc1.withdraw(withdrawInvalid)) {
            System.out.println("Withdrawing ₹" + withdrawInvalid + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } else {
            System.out.println("Withdrawing ₹" + withdrawInvalid + ": FAILED (Insufficient balance)");
            System.out.println("Current balance: ₹" + acc1.getBalance());
        }

        System.out.println("\n>>> 4. Creating Another Account");
        Account acc2 = new Account(1002, "Jane Smith", 30, 2000.0, "Current");
        displayAccount(acc2);

        System.out.println("\n>>> 5. All Accounts");
        displayAccount(acc1);
        displayAccount(acc2);

        System.out.println("==================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("==================================================");
    }

    private static void displayAccount(Account acc) {
        System.out.println("Account #" + acc.getAccountNumber() + " | " +
                acc.getName() + " (" + acc.getAge() + " yrs) | " +
                acc.getAccountType() + " | ₹" +
                acc.getBalance() + " | " +
                acc.getStatus());
    }
}