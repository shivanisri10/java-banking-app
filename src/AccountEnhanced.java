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

        if (age < 18) {
            this.age = 18;
        } else {
            this.age = age;
        }

        if (accountType != null && (accountType.equalsIgnoreCase("Savings") || accountType.equalsIgnoreCase("Current"))) {
            if (accountType.equalsIgnoreCase("Savings")) {
                this.accountType = "Savings";
            } else {
                this.accountType = "Current";
            }
        } else {
            this.accountType = "Savings";
        }

        double minBalance = getMinBalance();
        if (initialBalance < minBalance) {
            this.balance = minBalance;
        } else {
            this.balance = initialBalance;
        }

        this.status = "Active";
        this.pin = null;
    }

    private double getMinBalance() {
        if ("Savings".equalsIgnoreCase(this.accountType)) {
            return 500.0;
        } else if ("Current".equalsIgnoreCase(this.accountType)) {
            return 1000.0;
        }
        return 500.0;
    }

    public boolean deposit(double amount) {
        if (!"Active".equalsIgnoreCase(this.status) || amount <= 0) {
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount, int pin) {
        if (!"Active".equalsIgnoreCase(this.status)) {
            return false;
        }
        if (!verifyPin(pin)) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        double minBalance = getMinBalance();
        if ((this.balance - amount) < minBalance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public boolean closeAccount() {
        if ("Inactive".equalsIgnoreCase(this.status)) {
            return false;
        }
        this.status = "Inactive";
        return true;
    }

    public boolean reopenAccount() {
        if ("Active".equalsIgnoreCase(this.status)) {
            return false;
        }
        this.status = "Active";
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
        if (this.pin == null) {
            return false;
        }
        return this.pin == pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age >= 18) {
            this.age = age;
        }
    }
}