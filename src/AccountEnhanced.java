public class AccountEnhanced {
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    public AccountEnhanced(int accountNumber, String name, int age, double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age < 18 ? 18 : age;

        if ("Current".equalsIgnoreCase(accountType)) {
            this.accountType = "Current";
        } else {
            this.accountType = "Savings";
        }

        double minBalance = "Current".equals(this.accountType) ? 1000.0 : 500.0;
        this.balance = initialBalance < minBalance ? minBalance : initialBalance;
        this.status = "Active";
        this.pin = null;
    }

    public boolean deposit(double amount) {
        if (!"Active".equals(status) || amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount, int inputPin) {
        if (!"Active".equals(status) || amount <= 0 || pin == null || pin != inputPin) {
            return false;
        }
        // Allows current account withdrawals while preventing balance from going negative
        double minAllowedBalance = "Current".equals(accountType) ? 0.0 : 500.0;
        if (balance - amount < minAllowedBalance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean closeAccount() {
        if ("Inactive".equals(status)) return false;
        status = "Inactive";
        return true;
    }

    public boolean reopenAccount() {
        if ("Active".equals(status)) return false;
        status = "Active";
        return true;
    }

    public boolean setPin(int pin) {
        if (pin >= 1000 && pin <= 9999) {
            this.pin = pin;
            return true;
        }
        return false;
    }

    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public String getStatus() { return status; }
}