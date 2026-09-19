public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500.0;
    private double interestRate;

    public SavingsAccount(int accountNumber, String name, int age, double initialBalance, double interestRate) {
        super(accountNumber, name, age, initialBalance);
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.interestRate = interestRate;
    }

    @Override
    public double getMinimumBalance() {
        return MIN_BALANCE;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public void addInterest() throws InactiveAccountException {
        validateActive();
        double interest = getBalance() * (interestRate / 100);
        setBalance(getBalance() + interest);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.interestRate = interestRate;
    }
}
