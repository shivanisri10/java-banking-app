public class CurrentAccount extends Account {
    private static final double MIN_BALANCE = 1000.0;
    private static final double OVERDRAFT_LIMIT = 5000.0;
    private double overdraftUsed;

    public CurrentAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance);
        this.overdraftUsed = 0.0;
    }

    @Override
    public double getMinimumBalance() {
        return MIN_BALANCE;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public void withdraw(double amount, int pin) throws InvalidAmountException, InsufficientBalanceException, MinimumBalanceViolationException, InactiveAccountException, InvalidPinException {
        validateActive();
        if (hasPin() && !verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }

        double currentBal = getBalance();
        double availableOverdraft = getAvailableOverdraft();

        if (amount > (currentBal + availableOverdraft)) {
            throw new InsufficientBalanceException("Insufficient funds. Available: " + (currentBal + availableOverdraft) + " (including " + OVERDRAFT_LIMIT + " overdraft), Requested: " + amount);
        }

        if (amount <= currentBal) {
            setBalance(currentBal - amount);
        } else {
            double remaining = amount - currentBal;
            setBalance(0.0);
            overdraftUsed += remaining;
        }
    }

    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    public double getAvailableOverdraft() {
        return OVERDRAFT_LIMIT - overdraftUsed;
    }

    public boolean isUsingOverdraft() {
        return overdraftUsed > 0;
    }

    public void repayOverdraft(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > overdraftUsed) {
            throw new IllegalArgumentException("Amount exceeds overdraft used (" + overdraftUsed + ")");
        }
        this.overdraftUsed -= amount;
        setBalance(getBalance() + amount);
    }
}