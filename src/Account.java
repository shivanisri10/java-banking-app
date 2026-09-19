public class Account {
    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    public Account(int accountNumber, String name, int age, double initialBalance, String accountType) throws IllegalArgumentException {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Age must be at least 18");
        }
        if (!"Savings".equalsIgnoreCase(accountType) && !"Current".equalsIgnoreCase(accountType)) {
            throw new IllegalArgumentException("Invalid account type");
        }

        String normalizedType = "Current".equalsIgnoreCase(accountType) ? "Current" : "Savings";
        double minBalance = "Current".equals(normalizedType) ? MIN_BALANCE_CURRENT : MIN_BALANCE_SAVINGS;

        if (initialBalance < minBalance) {
            throw new IllegalArgumentException("Initial balance below minimum required");
        }

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = normalizedType;
        this.status = "Active";
        this.pin = null;
    }

    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        validateActive();
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public void withdraw(double amount, int pin) throws InvalidAmountException, InsufficientBalanceException, MinimumBalanceViolationException, InactiveAccountException, InvalidPinException {
        validateActive();
        if (this.pin == null) {
            throw new InvalidPinException("PIN not set");
        }
        if (this.pin != pin) {
            throw new InvalidPinException("Incorrect PIN");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient funds");
        }
        if (balance - amount < getMinimumBalance()) {
            throw new MinimumBalanceViolationException("Withdrawal violates minimum balance limit");
        }
        balance -= amount;
    }

    public void closeAccount() throws IllegalStateException {
        if ("Inactive".equals(status)) {
            throw new IllegalStateException("Account is already closed");
        }
        status = "Inactive";
    }

    public void reopenAccount() throws IllegalStateException {
        if ("Active".equals(status)) {
            throw new IllegalStateException("Account is already active");
        }
        status = "Active";
    }

    public void setPin(int pin) throws IllegalArgumentException {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }
        this.pin = pin;
    }

    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    private double getMinimumBalance() {
        return "Current".equals(accountType) ? MIN_BALANCE_CURRENT : MIN_BALANCE_SAVINGS;
    }

    private void validateActive() throws InactiveAccountException {
        if (!"Active".equals(status)) {
            throw new InactiveAccountException("Account is inactive");
        }
    }

    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public String getStatus() { return status; }
}